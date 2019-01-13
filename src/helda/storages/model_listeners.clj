(ns helda.storages.model-listeners
  (:require
    [helda.storages.common.repository :refer :all]
    [helda.storages.common.mongo :refer [mongo-repository]]
    )
  )

(defn init-model-listeners-mongo-storage [mongo-db]
  ;Issue if use keyword (str) didn't help somewhy
  (-> (mongo-repository mongo-db "model_listeners")
    (fk-index [:model] false)
    )
  )

(defn find-listeners-by-model [storage model]
  (find-all storage
    {:model model}
    (array-map :_id 1)
    )
  )

(defn find-listener-by-id [storage id]
  (find-one storage id)
  )

(defn save-listener [storage listener]
  (save storage listener)
  )
