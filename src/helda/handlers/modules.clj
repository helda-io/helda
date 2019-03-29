(ns helda.handlers.modules
  (:require
    [plumbing.core :refer [defnk]]
    [kekkonen.cqrs :refer :all]
    [schema.core :as s]

    [helda.schema :as hs]
    ;todo replace :as with :refer
    [helda.storages.modules :as storage]
    )
  )

;;
;; Handlers
;;

(defnk ^:query modules-list
  "List of available modules."
  {:responses {:default {:schema [s/Keyword]}}}
  [[:components db]]
  (success
    (storage/modules-list db)
    )
  )

(defnk ^:query get-module
  "Get module by id"
  [
    [:components db]
    [:data module-id :- s/Keyword]
    ]
  (success
    (storage/find-module-by-id db module-id)
    )
  )

(defnk ^:command save-module
  "Add module"
  {:responses {:default {:schema hs/Module}}}
  [
    [:components db]
    data :- hs/Module
    ]
  (success
    (storage/save-module db data)
    )
  )
