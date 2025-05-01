(ns employee-service.simple-server
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]))

;; Simple handler that only returns health status
(defn handler [request]
  (case (:uri request)
    "/health" (response {:status "UP"})
    "/" (response {:message "Employee Service API"})
    (response {:error "Not Found"})))

;; Wrap the handler with JSON middleware
(def app (wrap-json-response handler))

;; Start server function
(defn start-server [port]
  (println (str "Starting server on port " port))
  (jetty/run-jetty app {:port port :join? false}))

;; Main function to start the server
(defn -main [& args]
  (let [port (Integer/parseInt (or (first args) "8087"))]
    (start-server port)))