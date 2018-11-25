(ns helda.cqrs
  (:require
    [kekkonen.cqrs :refer :all]
    [helda.handlers.models :as m]
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
       }
       :context system
       }
      }
    )
  )
