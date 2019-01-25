(ns helda.handlers.models
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]

    [helda.schema :as hs]
    ;todo replace :as with :refer
    [helda.storages.models :as storage]
    [helda.storages.model-listeners :refer [find-listeners-by-model save-listener]]
    )
  )

;;
;; Handlers
;;

(defnk ^:query packages-list
  "List of available packages."
  {:responses {:default {:schema [s/Keyword]}}}
  [[:components db]]
  (success
    (storage/packages-list db)
    )
  )

(defnk ^:query package-models
  "Retrieves all models for package"
  {:responses {:default {:schema [hs/Model]}}}
  [
    [:components db]
    [:data package :- s/Keyword]
    ]
  (success
    (storage/find-models-by-package db package)
    )
  )

(defnk ^:query get-model
  "Get model by id"
  [
    [:components db]
    [:data name :- s/Keyword]
    ]
  (success
    (storage/find-model-by-name db name)
    )
  )

(defnk ^:query listeners-by-model
  "Get all model listeners per model"
  {:responses {:default {:schema [hs/ModelListener]}}}
  [
    [:components db]
    [:data model :- s/Keyword]
    ]
  (success
    (find-listeners-by-model db model)
    )
  )

(defnk ^:command save-model
  "Add model"
  {:responses {:default {:schema hs/Model}}}
  [
    [:components db]
    data :- hs/Model
    ]
  (success
    (storage/save-model db data)
    )
  )

(defnk ^:command add-model-listener
  "Add model listener"
  {:responses {:default {:schema hs/ModelListener}}}
  [
    [:components db]
    data :- hs/ModelListener
    ]
  (success
    (save-listener db data)
    )
  )
