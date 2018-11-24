(ns helda-storage.handler
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]
    )
  )

(defn create [system]
  (cqrs-api
    {:swagger {:ui "/"
               :spec "/swagger.json"
               :data {:info {:title "Helda-storage API"
                             :description "created with http://kekkonen.io"}}}
     :core {
       :handlers {
         :models [#'all-models #'get-model #'add-model]
       }
       :context system
       }
      }
    )
  )
