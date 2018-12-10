(ns helda.storages.entity-listeners
  (:require
    [helda.storages.common.repository :refer :all]
    [helda.storages.common.mongo :refer [mongo-repository]]
    )
  )

(defn init-entity-listeners-mongo-storage [mongo-db]
  ;Issue if use keyword (str) didn't help somewhy
  (-> (mongo-repository mongo-db "entity-listeners")
    (fk-index [:entity-id] false)
    )
  )

(defn find-listeners-by-entity-id [storage entity-id]
  (find-all storage
    {:entity-id entity-id}
    (array-map :_id 1)
    )
  )

(defn find-listener-by-id [storage id]
  (find-one storage id)
  )

(defn save-model-listener [storage listener]
  (save storage listener)
  )
