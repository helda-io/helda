(ns helda.actions.schema
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
  :action s/Str
  :source-entity Entity
  :target-entity Entity
  :action-ctx {s/Keyword Entity} ;request-ctx entities
  :params-ctx {s/Keyword s/Any}
  })

(s/defschema ActionResponse {
  :action s/Str
  :source-entity Entity
  :target-entity Entity
  :action-ctx {s/Keyword Entity} ;response-ctx entities
  :params-ctx {s/Keyword s/Any}
  :reasoning-msg s/Str
  })
