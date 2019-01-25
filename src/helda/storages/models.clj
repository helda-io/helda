(ns helda.storages.models
  (:require
    [helda.storages.common.repository :refer :all]
    [helda.storages.common.mongo :refer [mongo-repository]]
    )
  )

(defn init-models-mongo-storage [mongo-db]
  ;Issue if use keyword (str) didn't help somewhy
  (-> (mongo-repository mongo-db "models")
    (fk-index [:full-name] true)
    (fk-index [:package] false)
    )
  )

(defn packages-list [db]
  (find-distinct (:models-storage db) :package)
  )

(defn find-models-by-package [db package]
  (find-all (:models-storage db)
    {:package package}
    (array-map :full-name 1)
    )
  )

(defn find-model-by-name [db full-name]
  (find-one (:models-storage db) :full-name full-name)
  )

(defn save-model [db model]
  (save (:models-storage db) model)
  )
