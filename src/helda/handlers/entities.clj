(ns helda.handlers.entities
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]

    [helda.schema :as hs]
    )
  )

(defnk ^:query entities
  "Retrieves all entities for world. Can be filtered by tags or models."
  {:responses {:default {:schema [hs/Entity]}}}
  [
    [:db entities-storage]
    [:data
      world :- s/Keyword
      tags :- [s/Keyword]
      models :- [s/Keyword]
      ]
    ]
  (success (vals @entities-storage))
  )

(defnk ^:query get-entity
  "Get entity by id"
  {:responses {:default {:schema hs/Entity}}}
  [
    [:db entities-storage]
    [:data id :- s/Str]
    ]
  (success {:ping "pong"})
  )

(defnk ^:command add-entity
  "Add entity"
  {:responses {:default {:schema hs/Entity}}}
  [
    [:db entities-storage]
    data :- hs/Entity
    ]
  ;todo add-entity
  (success data)
  )
