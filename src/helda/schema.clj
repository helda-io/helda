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
    :fields {s/Keyword s/Str} ;{field schema}
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

(s/defschema ActionResponse {
  :action-ctx {s/Keyword Entity} ;updated entities
  })

(s/defschema ModelListener {
    :action-url s/Str
    :world s/Keyword
    :action s/Keyword
    :model s/Keyword
  })

(s/defschema EntityListener {
    :action-url s/Str
    :world s/Keyword
    :action s/Keyword
    :entity-id s/Str
  })

(s/defschema Event {
    :id s/Str
    :listener-id s/Str
    :action s/Keyword
    :action-ctx Entity
    :entity Entity
  })
