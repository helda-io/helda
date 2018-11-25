(ns helda.handlers.models
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]

    [helda.schema :as hs]
    )
  )

;;
;; Handlers
;;

(defnk ^:query packages-list
  "List of available packages. Can be filtered by tags."
  {:responses {:default {:schema [s/Keyword]}}}
  [[:db models-storage]]
  (success (vals @models-storage))
  )

(defnk ^:query package-models
  "Retrieves all models for package"
  {:responses {:default {:schema [hs/Model]}}}
  [
    [:db models-storage]
    [:data package :- s/Keyword]
    ]
  (success (vals @models-storage))
  )

(defnk ^:query get-model
  "Get model by id"
  [
    [:db models-storage]
    [:data name :- s/Keyword]
    ]
  (success {:ping "pong"})
  )

(defnk ^:command add-model
  "Add model"
  {:responses {:default {:schema hs/Model}}}
  [
    [:db models-storage]
    data :- hs/Model
    ]
  ;todo add-model
  (success data)
  )
