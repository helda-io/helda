(ns helda-server.listeners.invoker
  (:require
    [clj-http.client :as client]
    [helda.storages.entity-listeners :refer [find-listeners-by-entity-id]]
    [helda.storages.entities :refer [find-entity-by-id]]
    )
  )

(defn populate-action-event [db action-request]
  {
    :action (:action action-request)
    :source-entity (find-entity-by-id db (:source-entity-id action-request))
    :target-entity (find-entity-by-id db (:target-entity-id action-request))
    :action-ctx (zipmap
      (-> action-request :action-ctx keys)
      (->> action-request :action-ctx vals (map #(find-entity-by-id db %)))
      )
    }
  )

(defn invoke-listener [listener action-event]
  (println "Posting " action-event)
  (client/post (:action-url listener) {
    :form-params action-event
    :content-type :json
    })
  )

(defn fire-action [db action-request]
  (let [action-event (populate-action-event db action-request)]
    (map
      #(invoke-listener % db action-event)
      (find-listeners-by-entity-id db
        (:target-entity-id action-request)
        (:action action-request)
        )
      )
    )
  )
