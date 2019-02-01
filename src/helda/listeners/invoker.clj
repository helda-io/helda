(ns helda.listeners.invoker
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

(defn invoke-listener [db listener action-event]
  (println "Posting " action-event)
  (if-let [
    action-ctx (get-in
      (client/post (:action-url listener) {
        :form-params action-event
        :content-type :json
        :as :json-strict
        })
      [:body :action-ctx]
      )
    ]
    {:action-ctx
      (zipmap
        (keys action-ctx)
        (->> action-ctx vals (mapv #(save-entity db %)))
        )
      }
    )
  )

(defn fire-action [db action-request]
  (let [action-event (populate-action-event db action-request)]
    (->>
      (find-listeners-by-entity-id db
        (:target-entity-id action-request)
        (:action action-request)
        )
      (map #(invoke-listener db % action-event))
      first
      )
    )
  )
