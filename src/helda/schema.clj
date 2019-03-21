(ns helda.schema
  (:require
    [schema.core :as s]
    )
  )

(s/defschema ActionRequest{
  :action s/Str
  :source-entity-id s/Str
  :target-entity-id s/Str
  (s/optional-key :action-ctx) {s/Keyword s/Str} ;entity-id per key
  })

(s/defschema Action{
  :source-model s/Str
  (s/optional-key :request-ctx) {s/Keyword s/Str} ;model per key
  (s/optional-key :response-ctx) {s/Keyword s/Str} ;model per key
  })

(s/defschema Model {
    :full-name s/Keyword

    :package s/Keyword
    :name s/Keyword
    (s/optional-key :description) s/Str

    :extends [s/Keyword]
    :fields {s/Keyword s/Str} ;{field description or type at least}
    :actions {s/Keyword Action}
  })

(s/defschema World {
    :world s/Keyword
    (s/optional-key :description) s/Str
    :tags [s/Keyword]
  })

(s/defschema Entity {
    :world s/Keyword
    :model s/Keyword
    :tags [s/Keyword]
    (s/optional-key :description) s/Str

    :attrs {s/Keyword s/Any}

    (s/optional-key :id) s/Str
    (s/optional-key :parent-id) s/Str
  })

;todo get rid of duplication, let's have a common schema
(s/defschema ActionResponse {
  :action-ctx {s/Keyword Entity} ;updated entities
  :reasoning-msg s/Str
  })
