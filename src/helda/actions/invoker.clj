(ns helda.listeners.actions
  (:require
    [clj-http.client :as client]
    [helda.storages.entities :refer [find-entity-by-id save-entity]]
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

(defn invoke-action [db action-event]
  ;todo add error processing
  (println "Posting " action-event)
  (let [
    handler-response (client/post (:action-url listener) {
      :form-params action-event
      :content-type :json
      :as :json-strict
      })
    ]
    (println "Getting response " handler-response)
    (if-let [action-ctx (get-in handler-response [:body :action-ctx])]
      {:action-ctx
        (zipmap
          (keys action-ctx)
          (->> action-ctx vals (mapv #(save-entity db %)))
          )
        :reasoning-msg (get-in handler-response [:body :reasoning-msg])
        }
      )
    )
  )

(defn fire-action [db action-request]
  (let [action-event (populate-action-event db action-request)]
    (->>
      (map #(invoke-action db action-event))
      first
      )
    )
  )
