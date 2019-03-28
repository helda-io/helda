(ns helda.system
  (:require
    [helda.cqrs :as cqrs]

    [helda.storages.common.mongo :refer [init-mongo-db]]

    [helda.storages.models :refer [init-models-mongo-storage]]
    [helda.storages.modules :refer [init-modules-mongo-storage]]
    [helda.storages.worlds :refer [init-worlds-mongo-storage]]
    [helda.storages.entities :refer [init-entities-mongo-storage]]
    )
  )

(defn init-db [mongodb]
  {
    :models-storage (init-models-mongo-storage mongodb)
    :modules-storage (init-modules-mongo-storage mongodb)
    :worlds-storage (init-worlds-mongo-storage mongodb)
    :entities-storage (init-entities-mongo-storage mongodb)
    }
  )

(defn new-system [] {
   :components {
     :db (init-db (init-mongo-db))
     }
  })
