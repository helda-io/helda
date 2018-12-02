(ns helda.storages.worlds
  (:require
    [helda.storages.common.repository :refer :all]
    [helda.storages.common.mongo :refer [mongo-repository]]

    [monger.operators :refer :all]
    )
  )

(defn init-worlds-mongo-storage [mongo-db]
  ;Issue if use keyword (str) didn't help somewhy
  (-> (mongo-repository mongo-db "worlds")
    (fk-index [:world] true)
    ; (fk-index [:tags] false)
    )
  )

(defn tags-list [storage]
  (find-distinct storage :tags)
  )

(defn worlds-list [storage]
  (find-all storage {} (array-map :world 1))
  )

(defn find-worlds-by-tags [storage tags]
  (find-all storage {:tags {$in tags}} (array-map :world 1))
  )

(defn find-world-by-name [storage world-name]
  (find-one storage :world world-name)
  )

(defn create-world [storage world]
  (save storage world)
  )
