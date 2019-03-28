(ns helda.storages.modules
  (:require
    [helda.storages.common.repository :refer :all]
    [helda.storages.common.mongo :refer [mongo-repository]]
    )
  )

(defn init-modules-mongo-storage [mongo-db]
  ;Issue if use keyword (str) didn't help somewhy
  (-> (mongo-repository mongo-db "modules")
    (fk-index [:module-id] true)
    )
  )

(defn find-module-by-id [db module-id]
  (find-one (:modules-storage db) :module-id module-id)
  )

(defn save-module [db module]
  (save (:modules-storage db) module)
  )
