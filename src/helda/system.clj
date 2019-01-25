(ns helda.system
  (:require
    [helda.cqrs :as cqrs]

    [helda.storages.common.mongo :refer [init-mongo-db]]

    [helda.storages.models :refer [init-models-mongo-storage]]
    [helda.storages.worlds :refer [init-worlds-mongo-storage]]
    [helda.storages.entities :refer [init-entities-mongo-storage]]
    [helda.storages.model-listeners :refer [init-model-listeners-mongo-storage]]
    [helda.storages.entity-listeners :refer [init-entity-listeners-mongo-storage]]
    )
  )

(defn init-db [mongodb]
  {
    :models-storage (init-models-mongo-storage mongodb)
    :worlds-storage (init-worlds-mongo-storage mongodb)
    :entities-storage (init-entities-mongo-storage mongodb)
    :model-listeners-storage (init-model-listeners-mongo-storage mongodb)
    :entity-listeners-storage (init-entity-listeners-mongo-storage mongodb)
    :events-storage (atom {})
    }
  )

(defn new-system [] {
   :components {
     :db (init-db (init-mongo-db))
     }
  })
