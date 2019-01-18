(ns helda.main
  (:require
    [org.httpkit.server :as server]
    [helda.cqrs :as cqrs]
    [helda.system :as system]
    )
  (:gen-class)
  )

(defn -main [& [port]]
  (let [port (or port 3000)]
    (server/run-server
      (cqrs/create
        (system/new-system)
        )
      {:port port}
      )
    )
  )
