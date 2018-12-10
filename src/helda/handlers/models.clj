(ns helda.handlers.models
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]

    [helda.schema :as hs]
    [helda.storages.models :as storage]
    )
  )

;;
;; Handlers
;;

(defnk ^:query packages-list
  "List of available packages."
  {:responses {:default {:schema [s/Keyword]}}}
  [[:db models-storage]]
  (success
    (storage/packages-list models-storage)
    )
  )

(defnk ^:query package-models
  "Retrieves all models for package"
  {:responses {:default {:schema [hs/Model]}}}
  [
    [:db models-storage]
    [:data package :- s/Keyword]
    ]
  (success
    (storage/find-models-by-package models-storage package)
    )
  )

(defnk ^:query get-model
  "Get model by id"
  [
    [:db models-storage]
    [:data name :- s/Keyword]
    ]
  (success
    (storage/find-model-by-name models-storage name)
    )
  )

(defnk ^:command add-model
  "Add model"
  {:responses {:default {:schema hs/Model}}}
  [
    [:db models-storage]
    data :- hs/Model
    ]
  (success
    (storage/create-model models-storage data)
    )
  )

; (defnk ^:command add-model-listener
;   "Add model listener"
;   {:responses {:default {:schema hs/ModelListener}}}
;   [
;     [:db model-listeners-storage]
;     data :- hs/ModelListener
;     ]
;   ;todo add-listener
;   (success data)
;   )
