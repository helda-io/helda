(ns helda.storages.entities
  (:require
    [helda.storages.common.repository :refer :all]
    [helda.storages.common.mongo :refer [mongo-repository]]

    [monger.operators :refer :all]
    )
  )

(defn init-entities-mongo-storage [mongo-db]
  ;Issue if use keyword (str) didn't help somewhy
  (-> (mongo-repository mongo-db "entities")
    (fk-index [:world] false)
    (fk-index [:model] false)
    ; (fk-index [:tags] false)
    )
  )

(defn find-entities-by-world [db world]
  (find-all
    (:entity-listeners-storage db)
    {:world world}
    (array-map :_id 1)
    )
  )

(defn find-entities-by-world-and-tags [db world tags]
  (find-all (:entity-listeners-storage db)
    {:world world :tags {$in tags}}
    (array-map :_id 1)
    )
  )

(defn find-entities-by-world-and-models [db world models]
  (find-all (:entity-listeners-storage db)
    {:world world :model {$in models}}
    (array-map :_id 1)
    )
  )

(defn find-entities-by-tags-and-models [db world tags models]
  (find-all (:entity-listeners-storage db)
    {:world world :model {$in models} :tags {$in tags}}
    (array-map :_id 1)
    )
  )

(defn find-entity-by-id [db id]
  (find-one (:entity-listeners-storage db) id)
  )

(defn save-entity [db entity]
  (save (:entity-listeners-storage db) entity)
  )
