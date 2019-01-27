(ns helda.handlers.worlds
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]

    [helda.schema :as hs]
    [helda.storages.worlds :as storage]
    )
  ; (:use clojure.tools.trace)
  )

(defnk ^:query tags-list
  "List of available tags. Can be filtered by tags."
  {:responses {:default {:schema [s/Keyword]}}}
  [[:components db]]
  (success
    (storage/tags-list db)
    )
  )

(defnk ^:query worlds-list
  "List of available worlds. Can be filtered by tags."
  {:responses {:default {:schema [s/Keyword]}}}
  [[:components db]]
  (success
    (storage/worlds-list db)
    )
  )

(defnk ^:query worlds-by-tags
  "Find worlds by tags."
  {:responses {:default {:schema [s/Keyword]}}}
  [
    [:components db]
    [:data tags :- [s/Str]]
    ]
  (success
    (storage/find-worlds-by-tags db tags)
    )
  )

(defnk ^:query get-world
  "Get world by id"
  {:responses {:default {:schema hs/World}}}
  [
    [:components db]
    [:data world :- s/Str]
    ]
  (success
    (storage/find-world-by-name db world)
    )
  )

(defnk ^:command save-world
  "Add world"
  {:responses {:default {:schema hs/World}}}
  [
    [:components db]
    data :- hs/World
    ]
  ;todo add-entity
  (success
    (storage/save-world db data)
    )
  )
