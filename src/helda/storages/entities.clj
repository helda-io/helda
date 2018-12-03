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

(defn find-entities-by-world [storage world]
  (find-all storage {:world world} (array-map :_id 1))
  )

(defn find-entities-by-world-and-tags [storage world tags]
  (find-all storage
    {:world world :tags {$in tags}}
    (array-map :_id 1)
    )
  )

(defn find-entities-by-world-and-models [storage world models]
  (find-all storage
    {:world world :model {$in models}}
    (array-map :_id 1)
    )
  )

(defn find-entities-by-tags-and-models [storage world tags models]
  (find-all storage
    {:world world :model {$in models} :tags {$in tags}}
    (array-map :_id 1)
    )
  )

(defn save-entity [storage entity]
  (save storage entity)
  )
