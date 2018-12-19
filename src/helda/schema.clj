(ns helda.schema
  (:require
    [schema.core :as s]
    )
  )

(s/defschema Model {
    :full-name s/Keyword

    :package s/Keyword
    :name s/Keyword
    (s/optional-key :description) s/Str

    :extends [s/Keyword]
    :attrs {s/Keyword s/Str} ;{attr schema}
    :actions {s/Keyword s/Keyword} ;{action model} ;todo add description
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
