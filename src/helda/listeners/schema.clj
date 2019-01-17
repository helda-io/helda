(ns helda-server.listeners.schema
  (:require
    [schema.core :as s]
    )
  )

(s/defschema Entity {
    :world s/Keyword
    :model s/Keyword
    :tags [s/Keyword]
    (s/optional-key :description) s/Str

    :attrs {s/Keyword s/Any}

    :id s/Str
    (s/optional-key :parent-id) s/Str
  })

(s/defschema ActionEvent {
  :source-entity Entity
  :target-entity Entity
  (s/optional-key :action-ctx) {s/Keyword Entity}
  })

(s/defschema ActionResponse {
  :action-ctx {s/Keyword Entity} ;updated entities
  })
