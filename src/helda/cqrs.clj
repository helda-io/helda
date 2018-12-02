(ns helda.cqrs
  (:require
    [kekkonen.cqrs :refer :all]
    [helda.handlers.models :as m]
    [helda.handlers.worlds :as w]
    [helda.handlers.entities :as e]
    [helda.handlers.listeners :as l]
    )
  )

(defn create [system]
  (cqrs-api
    {:swagger {:ui "/"
               :spec "/swagger.json"
               :data {:info {:title "Helda API"
                             :description "created with http://kekkonen.io"}}}
     :core {
       :handlers {
        :models [
          #'m/packages-list
          #'m/package-models
          #'m/get-model
          #'m/add-model
          ]
        :worlds [
          #'w/tags-list
          #'w/worlds-by-tags
          #'w/worlds-list
          #'w/get-world
          #'w/add-world
          ]
        :entities [
          #'e/entities
          #'e/get-entity
          #'e/add-entity
          ]
        :listeners [
          #'l/listeners
          #'l/get-listener
          #'l/add-listener
          ]
       }
       :context system
       }
      }
    )
  )
