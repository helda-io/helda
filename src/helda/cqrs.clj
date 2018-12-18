(ns helda.cqrs
  (:require
    [kekkonen.cqrs :refer :all]
    [helda.handlers.models :as m]
    [helda.handlers.worlds :as w]
    [helda.handlers.entities :as e]
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
          #'m/save-model
          #'m/listeners-by-model
          #'m/add-model-listener
          ]
        :worlds [
          #'w/tags-list
          #'w/worlds-by-tags
          #'w/worlds-list
          #'w/get-world
          #'w/save-world
          ]
        :entities [
          #'e/entities-by-world
          #'e/entities-by-models
          #'e/entities-by-tags
          #'e/entities-by-tags-and-models
          #'e/get-entity
          #'e/save-entity
          #'e/add-entity-listener
          ]
       }
       :context system
       }
      }
    )
  )
