(ns employee-service.db
  (:require [taoensso.timbre :as timbre]))

;; Initialize atom for in-memory database
(def employees-atom (atom {}))

;; Initialize in-memory database with sample data
(defn init-db []
  (reset! employees-atom
          {"1" {:id "1"
                :firstName "John"
                :lastName "Doe"
                :email "john.doe@example.com"
                :department "IT"
                :salary 75000.0}
           "2" {:id "2"
                :firstName "Jane"
                :lastName "Smith"
                :email "jane.smith@example.com"
                :department "HR"
                :salary 65000.0}
           "3" {:id "3"
                :firstName "Michael"
                :lastName "Brown"
                :email "michael.brown@example.com"
                :department "Finance"
                :salary 85000.0}
           "4" {:id "4"
                :firstName "Sarah"
                :lastName "Johnson"
                :email "sarah.johnson@example.com"
                :department "Marketing"
                :salary 70000.0}
           "5" {:id "5"
                :firstName "David"
                :lastName "Williams"
                :email "david.williams@example.com"
                :department "Operations"
                :salary 72000.0}})
  (timbre/info "Initialized in-memory employee database with sample data"))

;; Get all employees
(defn get-all-employees []
  (timbre/debug "Retrieving all employees from in-memory database")
  (vals @employees-atom))

;; Get employee by ID
(defn get-employee-by-id [id]
  (timbre/debug (str "Finding employee with ID: " id))
  (get @employees-atom id))

;; Create new employee
(defn create-employee [employee-data]
  (let [id (str (java.util.UUID/randomUUID))
        new-employee (assoc employee-data :id id)]
    (swap! employees-atom assoc id new-employee)
    (timbre/info (str "Created new employee with ID: " id))
    new-employee))

;; Update employee
(defn update-employee [id employee-data]
  (timbre/debug (str "Attempting to update employee with ID: " id))
  (if-let [existing (get @employees-atom id)]
    (let [updated-employee (assoc employee-data :id id)]
      (swap! employees-atom assoc id updated-employee)
      (timbre/info (str "Successfully updated employee with ID: " id))
      updated-employee)
    (do
      (timbre/warn (str "Employee with ID: " id " not found"))
      nil)))

;; Delete employee
(defn delete-employee [id]
  (timbre/debug (str "Attempting to delete employee with ID: " id))
  (if-let [_ (get @employees-atom id)]
    (do
      (swap! employees-atom dissoc id)
      (timbre/info (str "Successfully deleted employee with ID: " id))
      true)
    (do
      (timbre/warn (str "Employee with ID: " id " not found for deletion"))
      false)))

;; Initialize the database when this namespace is loaded
(init-db)
