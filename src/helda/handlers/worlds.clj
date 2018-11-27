(ns helda.handlers.worlds
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]

    [helda.schema :as hs]
    )
  )

(defnk ^:query tags-list
  "List of available tags. Can be filtered by tags."
  {:responses {:default {:schema [s/Keyword]}}}
  [[:db worlds-storage]]
  (success (vals @worlds-storage))
  )

(defnk ^:query worlds-list
  "List of available worlds. Can be filtered by tags."
  {:responses {:default {:schema [s/Keyword]}}}
  [[:db worlds-storage]]
  (success (vals @worlds-storage))
  )

(defnk ^:query get-world
  "Get world by id"
  {:responses {:default {:schema hs/World}}}
  [
    [:db worlds-storage]
    [:data id :- s/Str]
    ]
  (success {:ping "pong"})
  )

(defnk ^:command add-world
  "Add world"
  {:responses {:default {:schema hs/World}}}
  [
    [:db worlds-storage]
    data :- hs/World
    ]
  ;todo add-entity
  (success data)
  )
