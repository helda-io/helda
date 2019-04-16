(ns helda.actions.invoker
  (:require
    [clj-http.client :as client]
    [helda.storages.entities :refer [find-entity-by-id save-entity]]
    [helda.storages.modules :refer [find-module-by-id]]
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

(defn invoke-rest [module action-event]
  ;todo add error processing
  (println "Posting " action-event)
  (let [
    handler-response (client/post (:url module) {
      :form-params action-event
      :content-type :json
      :as :json-strict
      })
    ]
    (println "Getting response " handler-response)
    (:body handler-response)
    )
  )

(defn invoke-script [module action-event]
  (println "Invoking module " module " for action event " action-event)
  (binding [*ns* (create-ns (symbol (:module-id module)))]
    (-> module :script load-string)
    (apply (:action action-event) action-event)
    )
  )

(defn process-action-response [db resp]
  (println "Processing response: " resp)
  (if-let [action-ctx (:action-ctx resp)]
    {
      :action-ctx
      (zipmap
        (keys action-ctx)
        (->> action-ctx vals (mapv #(save-entity db %)))
        )
      :params-ctx (:params-ctx resp)
      :reasoning-msg (:reasoning-msg resp)
      }
    )
  )

(defn lookup-module [db action-event]
  (some->> [:target-entity :actions (-> :action action-event keyword)]
    (get-in action-event)
    (find-module-by-id db)
    )
  )

(defn fire-action [db action-request]
  (let [
    action-event (populate-action-event db action-request)
    module (lookup-module db action-event)
    ]
    (process-action-response db
      (if (-> module :kind (= :rest))
        (invoke-rest module action-event)
        (invoke-script module action-event)
        )
      )
    )
  )
