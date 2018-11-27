(ns helda.handlers.listeners
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]

    [helda.schema :as hs]
    )
  )

(defnk ^:query listeners
  "Retrieves all listeners for world. Can be filtered by models or entities."
  {:responses {:default {:schema [hs/Listener]}}}
  [
    [:db listeners-storage]
    [:data
      world :- s/Keyword
      models :- [s/Keyword]
      entities :- [s/Str]
      ]
    ]
  (success (vals @listeners-storage))
  )

(defnk ^:query get-listener
  "Get listener by id"
  [
    [:db listeners-storage]
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
