(ns helda.storages.common.mongo
  (:require
    [environ.core :refer [env]]
    [monger.core :as mg]
    [monger.collection :as mc]
    [monger.query :as mq]
    [monger.operators :refer :all]
    [clojure.set :refer [rename-keys]]

    [helda.storages.common.repository :refer :all]
    )
  (:import org.bson.types.ObjectId)
  ; (:use clojure.tools.trace)
  )

(def db-url (if-let [url (env :mongodb-uri)] url "mongodb://127.0.0.1/helda"))
(defn init-mongo-db []
  ((mg/connect-via-uri db-url) :db)
  )

;todo question about pool in monger

(defn mongify [obj]
  (if-let [id (:id obj)]
    (-> obj (assoc :_id (ObjectId. id)) (dissoc :id) )
    obj
    )
  )

(defn unmongify [result]
  (if result
    (-> result
      (assoc :id (str (:_id result)))
      (dissoc :_id)
      )
    )
  )

(deftype MongoRepository [db collection-name]
  Repository

  (index [this fields unique]
    ; unique can be a boolean or a keyword
    (let [unique? (case unique :unique true :non-unique false unique)]
      (mc/ensure-index db collection-name fields {:unique unique?})
      )
    )

  (find-one [this id]
    (unmongify (mc/find-map-by-id db collection-name (ObjectId. id)))
    )

  (find-one [this field value]
    (->
      (mq/with-collection db collection-name
        (mq/find (mongify {field value}))
        (mq/sort nil)
        )
      first
      unmongify
      )
    )

  (find-all [this where-fields sort-fields]
    (map unmongify
      (mq/with-collection db collection-name
        (mq/find (mongify where-fields))
        (mq/sort sort-fields)
        )
      )
    )

  (find-distinct [this field]
    (map #(get % field)
      (mc/distinct db collection-name field)
      )
    )

  (save [this rec]
    (->> rec
      (mongify)
      (mc/save-and-return db collection-name)
      (unmongify)
      )
    )

  (delete-one [this id] (mc/remove-by-id db collection-name (ObjectId. id)))
  (delete-all [this fields] (mc/remove db collection-name fields))
  )

(defn mongo-repository [db collection-name] (MongoRepository. db collection-name))
