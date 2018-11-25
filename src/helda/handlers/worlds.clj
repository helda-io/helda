(ns helda.handlers.worlds
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]

    [helda.schema :as hs]
    )
  )

(defnk ^:query worlds-list
  "List of available worlds. Can be filtered by tags."
  {:responses {:default {:schema [s/Keyword]}}}
  [[:db entities-storage]]
  (success (vals @entities-storage))
  )

(defnk ^:query entities
  "Retrieves all entities for world. Can be filtered by tags or models."
  {:responses {:default {:schema [hs/Entity]}}}
  [
    [:db entities-storage]
    [:data world :- s/Keyword tags :- [s/Keyword] models :- [s/Keyword]]
    ]
  (success (vals @entities-storage))
  )

  (defnk ^:query get-entity
    "Get entity by id"
    [
      [:db entities-storage]
      [:data name :- s/Keyword]
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
