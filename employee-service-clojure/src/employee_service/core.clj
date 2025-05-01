(ns employee-service.core
  (:require [employee-service.handler :refer [app]]
            [ring.adapter.jetty :refer [run-jetty]]
            [taoensso.timbre :as timbre])
  (:gen-class))

(defn start-server [port]
  (timbre/info (str "Starting Employee Service Clojure on port " port))
  (run-jetty app {:port port :join? false}))

(defn -main
  "Start the server with optional port argument"
  [& args]
  (let [port (if (seq args)
               (Integer/parseInt (first args))
               8087)]
    (start-server port)))
