(ns helda.actions.invoker
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
    :params-ctx (:params-ctx action-request)
    }
  )

(defn invoke-action [db url action-event]
  ;todo add error processing
  (println "Posting " action-event)
  (let [
    handler-response (client/post url {
      :form-params action-event
      :content-type :json
      :as :json-strict
      })
    ]
    (println "Getting response " handler-response)
    (if-let [action-ctx (get-in handler-response [:body :action-ctx])]
      {
        :action-ctx
        (zipmap
          (keys action-ctx)
          (->> action-ctx vals (mapv #(save-entity db %)))
          )
        :params-ctx (get-in handler-response [:body :params-ctx])
        :reasoning-msg (get-in handler-response [:body :reasoning-msg])
        }
      )
    )
  )

(defn lookup-action-url [db action-event]
  (let [
    module-id (get-in action-event [:target-entity :actions (:action action-event)])
    ]
    module-id ;todo lookup module details from db
    )
  )

(defn fire-action [db action-request]
  (let [
    action-event (populate-action-event db action-request)
    url (lookup-action-url action-event)
    ]
    (invoke-action db url action-event)
    )
  )
