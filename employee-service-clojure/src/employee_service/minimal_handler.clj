(ns employee-service.minimal-handler
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]))

;; Define the routes
(defroutes app-routes
  ;; Health check endpoint
  (GET "/health" []
    (response {:status "UP"}))
  
  ;; Default route
  (GET "/" []
    (response {:message "Employee Service API"}))
    
  ;; Not found handler
  (route/not-found "Not Found"))

;; Create app with middleware
(def app
  (-> app-routes
      wrap-json-response
      (wrap-defaults api-defaults)))