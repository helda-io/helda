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

(defn tags-list [db]
  (find-distinct (:worlds-storage db) :tags)
  )

(defn worlds-list [db]
  (find-all (:worlds-storage db) {} (array-map :world 1))
  )

(defn find-worlds-by-tags [db tags]
  (find-all (:worlds-storage db) {:tags {$in tags}} (array-map :world 1))
  )

(defn find-world-by-name [db world-name]
  (find-one (:worlds-storage db) :world world-name)
  )

(defn save-world [db world]
  (save (:worlds-storage db) world)
  )
