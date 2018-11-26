(ns helda.handlers.listeners
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]

    [helda.schema :as hs]
    )
  )

(defnk ^:query listeners
  "Retrieves all listeners for world. Can be filtered by tags, models or entities."
  {:responses {:default {:schema [hs/Listener]}}}
  [
    [:db entities-storage]
    [:data
      world :- s/Keyword
      tags :- [s/Keyword]
      models :- [s/Keyword]
      entities :- [s/Str]
      ]
    ]
  (success (vals @entities-storage))
  )

(defnk ^:query get-listener
  "Get listener by id"
  [
    [:db entities-storage]
    [:data id :- s/Str]
    ]
  (success {:ping "pong"})
  )

(defnk ^:command add-listener
  "Add listener"
  {:responses {:default {:schema hs/Listener}}}
  [
    [:db listeners-storage]
    data :- hs/Listener
    ]
  ;todo add-listener
  (success data)
  )
