(defproject helda "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [
    [org.clojure/clojure "1.8.0"]
    [http-kit "2.1.19"]
    [metosin/palikka "0.5.1"]
    [metosin/kekkonen "0.3.2"]
    [com.novemberain/monger "3.1.0"]
    [environ "1.0.0"]
    [org.clojure/tools.trace "0.7.9"]
  ]

  :main helda.main

  :profiles {:uberjar {:aot [helda.main]
                       :main helda.main
                       :uberjar-name "helda.jar"}})
