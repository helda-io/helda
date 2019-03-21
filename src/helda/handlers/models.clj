(ns helda.handlers.models
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]

    [helda.schema :as hs]
    ;todo replace :as with :refer
    [helda.storages.models :as storage]
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
