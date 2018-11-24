(ns helda-storage.system
  (:require [com.stuartsierra.component :as component]
            [palikka.components.http-kit :as http-kit]
            [helda-storage.handler :as handler]))

(defn new-system [config]
  (component/map->SystemMap
    {
      :db {
        :models-storage (atom {})
        :listeners-storage (atom {})
        :relations-storage (atom {})
        :worlds-storage (atom {})
        :instances-storage (atom {})
        :events-storage (atom {})
        }
     :http (component/using
             (http-kit/create
               (:http config)
               {
                 :fn
                 (if (:dev-mode? config)
                    ; re-create handler on every request
                    (fn [system] #((handler/create system) %))
                    handler/create
                    )
                  }
                )
             [:db])
      }
    )
  )
