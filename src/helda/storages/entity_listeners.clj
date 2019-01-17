(ns helda.storages.entity-listeners
  (:require
    [helda.storages.common.repository :refer :all]
    [helda.storages.common.mongo :refer [mongo-repository]]
    )
  )

(defn init-entity-listeners-mongo-storage [mongo-db]
  ;Issue if use keyword (str) didn't help somewhy
  (-> (mongo-repository mongo-db "entity_listeners")
    (fk-index [:entity-id :action] false)
    )
  )

(defn find-listeners-by-entity-id [storage entity-id action]
  (find-all storage
    {:entity-id entity-id :action action}
    (array-map :_id 1)
    )
  )

(defn find-listener-by-id [storage id]
  (find-one storage id)
  )

(defn save-listener [storage listener]
  (save storage listener)
  )
