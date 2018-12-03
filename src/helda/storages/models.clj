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

(defn packages-list [storage]
  (find-distinct storage :package)
  )

(defn find-models-by-package [storage package]
  (find-all storage
    {:package package}
    (array-map :full-name 1)
    )
  )

(defn find-model-by-name [storage full-name]
  (find-one storage :full-name full-name)
  )

;todo rename to save-model
(defn create-model [storage model]
  (save storage model)
  )
