(ns helda.handlers.entities
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]

    [helda.schema :as hs]

    [helda.storages.entities :as storage]
    ;todo get rid of :as
    [helda.storages.entity-listeners :as listeners-storage]
    )
  )

(defnk ^:query entities-by-world
  "Retrieves all entities for world."
  {:responses {:default {:schema [hs/Entity]}}}
  [
    [:components db]
    [:data world :- s/Keyword]
    ]
  (success
    (storage/find-entities-by-world entities-storage world)
    )
  )

(defnk ^:query entities-by-models
  "Retrieves all entities for world filtered by models."
  {:responses {:default {:schema [hs/Entity]}}}
  [
    [:components db]
    [:data world :- s/Keyword models :- [s/Keyword]]
    ]
  (success
    (storage/find-entities-by-world-and-models entities-storage world models)
    )
  )

(defnk ^:query entities-by-tags
  "Retrieves all entities for world filtered by tags."
  {:responses {:default {:schema [hs/Entity]}}}
  [
    [:components db]
    [:data world :- s/Keyword tags :- [s/Keyword]]
    ]
  (success
    (storage/find-entities-by-world-and-tags entities-storage world tags)
    )
  )

(defnk ^:query entities-by-tags-and-models
  "Retrieves all entities for world filtered by tags or models."
  {:responses {:default {:schema [hs/Entity]}}}
  [
    [:components db]
    [:data
      world :- s/Keyword
      tags :- [s/Keyword]
      models :- [s/Keyword]
      ]
    ]
  (success
    (storage/find-entities-by-tags-and-models entities-storage world tags models)
    )
  )

(defnk ^:query get-entity
  "Get entity by id"
  {:responses {:default {:schema hs/Entity}}}
  [
    [:components db]
    [:data id :- s/Str]
    ]
  (success
    (storage/find-entity-by-id entities-storage id)
    )
  )

(defnk ^:query listeners-by-entity-id
  "Get all entity listeners per id"
  {:responses {:default {:schema [hs/EntityListener]}}}
  [
    [:components db]
    [:data entity-id :- s/Str]
    ]
  (success
    (listeners-storage/find-listeners-by-entity-id
      entity-listeners-storage entity-id
      )
    )
  )

(defnk ^:command save-entity
  "Add entity"
  {:responses {:default {:schema hs/Entity}}}
  [
    [:components db]
    data :- hs/Entity
    ]
  ;todo add-entity
  (success
    (storage/save-entity entities-storage data)
    )
  )

(defnk ^:command add-entity-listener
  "Add entity listener"
  {:responses {:default {:schema hs/EntityListener}}}
  [
    [:components db]
    data :- hs/EntityListener
    ]
  (success
    (listeners-storage/save-listener entity-listeners-storage data)
    )
  )
