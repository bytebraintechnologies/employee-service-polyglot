(ns employee-service.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.util.http-response :as response]
            [taoensso.timbre :as timbre]
            [employee-service.db :as db]
            [employee-service.logging :refer [configure-logging]]))

;; Initialize logging
(configure-logging)

;; Validation functions
(defn validate-employee [employee]
  (cond
    (nil? (:firstName employee)) "First name is required"
    (nil? (:lastName employee)) "Last name is required"
    (nil? (:email employee)) "Email is required"
    :else nil))

;; Routes
(defroutes app-routes
  ;; Get all employees
  (GET "/api/employees" []
    (try
      (timbre/info "Request to get all employees")
      (let [employees (db/get-all-employees)]
        (timbre/info (str "Retrieved " (count employees) " employees successfully"))
        (response/ok employees))
      (catch Exception e
        (timbre/error (str "Error retrieving employees: " (.getMessage e)))
        (response/internal-server-error {:message "Error retrieving employees" 
                                        :error (.getMessage e)}))))
  
  ;; Get employee by ID
  (GET "/api/employees/:id" [id]
    (try
      (timbre/info (str "Request to get employee with ID: " id))
      (if-let [employee (db/get-employee-by-id id)]
        (do
          (timbre/info (str "Retrieved employee with ID: " id " successfully"))
          (response/ok employee))
        (do
          (timbre/warn (str "Employee with ID: " id " not found"))
          (response/not-found {:message (str "Employee with ID: " id " not found")})))
      (catch Exception e
        (timbre/error (str "Error retrieving employee: " (.getMessage e)))
        (response/internal-server-error {:message "Error retrieving employee" 
                                        :error (.getMessage e)}))))
  
  ;; Create employee
  (POST "/api/employees" {body :body}
    (try
      (timbre/info "Request to create new employee")
      
      ;; Validate employee data
      (if-let [error (validate-employee body)]
        (do
          (timbre/warn (str "Failed to create employee: " error))
          (response/bad-request {:message error}))
        
        ;; Create employee
        (let [new-employee (db/create-employee body)]
          (timbre/info (str "Employee created successfully with ID: " (:id new-employee)))
          (response/created (str "/api/employees/" (:id new-employee)) new-employee)))
      (catch Exception e
        (timbre/error (str "Error creating employee: " (.getMessage e)))
        (response/internal-server-error {:message "Error creating employee" 
                                        :error (.getMessage e)}))))
  
  ;; Update employee
  (PUT "/api/employees/:id" [id :as {body :body}]
    (try
      (timbre/info (str "Request to update employee with ID: " id))
      
      (if-let [updated-employee (db/update-employee id body)]
        (do
          (timbre/info (str "Employee with ID: " id " updated successfully"))
          (response/ok updated-employee))
        (do
          (timbre/warn (str "Employee with ID: " id " not found for update"))
          (response/not-found {:message (str "Employee with ID: " id " not found")})))
      (catch Exception e
        (timbre/error (str "Error updating employee: " (.getMessage e)))
        (response/internal-server-error {:message "Error updating employee" 
                                        :error (.getMessage e)}))))
  
  ;; Delete employee
  (DELETE "/api/employees/:id" [id]
    (try
      (timbre/info (str "Request to delete employee with ID: " id))
      
      (if (db/delete-employee id)
        (do
          (timbre/info (str "Employee with ID: " id " deleted successfully"))
          (response/no-content))
        (do
          (timbre/warn (str "Employee with ID: " id " not found for deletion"))
          (response/not-found {:message (str "Employee with ID: " id " not found")})))
      (catch Exception e
        (timbre/error (str "Error deleting employee: " (.getMessage e)))
        (response/internal-server-error {:message "Error deleting employee" 
                                        :error (.getMessage e)}))))
  
  ;; Health check
  (GET "/health" []
    (timbre/info "Health check requested")
    (response/ok {:status "UP" :service "Employee Service Clojure"}))
  
  ;; Not found handler
  (route/not-found "Not Found"))

;; Create app with middleware
(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      (wrap-json-response)
      (wrap-cors :access-control-allow-origin [#".*"]
                :access-control-allow-methods [:get :put :post :delete :options])
      (wrap-defaults api-defaults)))

;; Startup log message
(timbre/info "Employee Service Clojure initialized and ready to serve")
