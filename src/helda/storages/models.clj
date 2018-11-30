(ns helda.storages.models
  (:require
    [helda.storages.common.repository :refer :all]
    [helda.storages.common.mongo :refer [mongo-repository]]
    )
  )

(def ^:dynamic models-storage nil)

(defn init-models-storage [mongo-db]
  ;Issue if use keyword (str) didn't help somewhy
  (-> (mongo-repository mongo-db "models")
    (fk-index [:full-name] true)
    (fk-index [:package] false)
    )
  )

(defn packages-list []
  (find-distinct models-storage :package)
  )

(defn find-models-by-package [package]
  (find-all models-storage
    {:package package}
    (array-map :full-name 1)
    )
  )

(defn create-model [model]
  (save models-storage model)
  )
