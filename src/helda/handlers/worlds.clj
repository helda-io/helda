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
  [[:db worlds-storage]]
  (success
    (storage/tags-list worlds-storage)
    )
  )

(defnk ^:query worlds-list
  "List of available worlds. Can be filtered by tags."
  {:responses {:default {:schema [s/Keyword]}}}
  [[:db worlds-storage]]
  (success
    (storage/worlds-list worlds-storage)
    )
  )

(defnk ^:query worlds-by-tags
  "Find worlds by tags."
  {:responses {:default {:schema [s/Keyword]}}}
  [
    [:db worlds-storage]
    [:data tags :- [s/Str]]
    ]
  (success
    (storage/find-worlds-by-tags worlds-storage tags)
    )
  )

(defnk ^:query get-world
  "Get world by id"
  {:responses {:default {:schema hs/World}}}
  [
    [:db worlds-storage]
    [:data world :- s/Str]
    ]
  (success
    (storage/find-world-by-name worlds-storage world)
    )
  )

(defnk ^:command save-world
  "Add world"
  {:responses {:default {:schema hs/World}}}
  [
    [:db worlds-storage]
    data :- hs/World
    ]
  ;todo add-entity
  (success
    (storage/save-world worlds-storage data)
    )
  )
