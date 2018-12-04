(ns helda.schema
  (:require
    [schema.core :as s]
    )
  )

(s/defschema Relation {
    :relation-type s/Keyword
    :src-attr s/Keyword
    :dst-model s/Keyword
    :dst-attr s/Keyword
  })

(s/defschema Model {
    :full-name s/Keyword
    :package s/Keyword
    :name s/Keyword
    (s/optional-key :description) s/Str
    :extends [s/Keyword]
    :relaions [Relation]
    :attrs {s/Keyword s/Keyword}
  })

(s/defschema Listener {
    :id s/Str
    :action-url s/Str
    :world s/Keyword
    :type s/Keyword
    :obj-id s/Str
  })

(s/defschema World {
    :world s/Keyword
    :description s/Str
    :tags [s/Keyword]
  })

(s/defschema Entity {
    :world s/Keyword
    :model s/Keyword
    :tags [s/Keyword]
    :attrs {s/Keyword s/Any}
  })

(s/defschema Event {
    :id s/Str
    :listener-type s/Keyword
    :world s/Keyword
    :model s/Str
    :instance Object
  })
