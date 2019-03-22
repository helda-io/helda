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
  (s/optional-key :params-ctx) {s/Keyword s/Any}
  })

;todo get rid of duplication, let's have a common schema
(s/defschema ActionResponse {
  :action-ctx {s/Keyword Entity} ;updated entities
  (s/optional-key :params-ctx) {s/Keyword s/Any}
  :reasoning-msg s/Str
  })

(s/defschema ActionCtxMeta{
  :entities {s/Keyword s/Str} ;model per key
  :params-ctx {s/Keyword s/Str} ;simple properties
  })

(s/defschema Action{
  :source-model s/Str
  :request-ctx ActionCtxMeta
  :response-ctx ActionCtxMeta
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
