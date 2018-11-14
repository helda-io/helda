(ns helda.schema
  (:require [plumbing.core :refer [defnk]]
            [kekkonen.cqrs :refer :all]
            [schema.core :as s])
  )

(s/defschema Model
  {
    :full-name s/Str
    :package s/Keyword
    :name s/Keyword
    (s/optional-key :description) s/Str
    :extends [s/Keyword]
    :relaions [Relation]
    :attrs {s/Keyword s/Keyword}
    }
  )

(s/defschema Listener
  {
    :id s/Str
    :action-url s/Str
    :world s/Keyword
    :type s/Keyword
    :obj-id s/Str
    }
  )

(s/defschema World
  {
    :world s/Keyword
    :description s/String
    }
  )

(s/defschema Instance
  {
    :id s/Str
    :world s/Keyword
    :model s/Keyword
    :attrs {s/Keyword s/Any}
    }
  )

(s/defschema Event
  {
    :id s/Str
    :type s/Keyword
    :world s/Keyword
    :model s/Str
    :instance Instance
    :changed-attrs {s/Keyword s/Any}
    }
  )
