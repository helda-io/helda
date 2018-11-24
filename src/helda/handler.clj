(ns helda-storage.handler
  (:require [plumbing.core :refer [defnk]]
            [kekkonen.cqrs :refer :all]
            [schema.core :as s])
  )

;;
;; Handlers
;;

(defnk ^:query all-models
  "Retrieves all models"
  {:responses {:default {:schema [Model]}}}
  [[:db models-storage]]
  (success (vals @models-storage))
  )

(defnk ^:query get-model
  "Get model by id"
  [
    [:db models-storage]
    [:data id :- s/Keyword]
    ]
  (success {:ping "pong"})
  )

(defnk ^:command add-model
  "Add model"
  {:responses {:default {:schema Model}}}
  [
    [:db models-storage]
    data :- Model
    ]
  ;todo add-model  
  (success data)
  )

;;
;; Application
;;

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
