(ns helda.system
  (:require
    [com.stuartsierra.component :as component]
    [palikka.components.http-kit :as http-kit]
    [helda.cqrs :as cqrs]

    [helda.storages.common.mongo :refer [init-mongo-db]]

    [helda.storages.models :refer [init-models-mongo-storage]]
    )
  )

(defn init-db [mongodb]
  {
    :models-storage (init-models-mongo-storage mongodb)
    :listeners-storage (atom {})
    :worlds-storage (atom {})
    :entities-storage (atom {})
    :events-storage (atom {})
    }
  )

(defn new-system [config]
  (component/map->SystemMap
    {
     :db (init-db (init-mongo-db))
     :http (component/using
             (http-kit/create
               (:http config)
               {
                 :fn
                 (if (:dev-mode? config)
                    ; re-create handler on every request
                    (fn [system] #((cqrs/create system) %))
                    cqrs/create
                    )
                  }
                )
             [:db])
      }
    )
  )
