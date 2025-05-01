package models

import (
	"employee-service/utils"
	"github.com/google/uuid"
	"sync"
)

// Employee represents an employee entity
type Employee struct {
	ID         string  `json:"id"`
	FirstName  string  `json:"firstName" binding:"required"`
	LastName   string  `json:"lastName" binding:"required"`
	Email      string  `json:"email" binding:"required,email"`
	Department string  `json:"department"`
	Salary     float64 `json:"salary"`
}

// EmployeeRepository manages the employee data
type EmployeeRepository struct {
	employees map[string]Employee
	mutex     sync.RWMutex
}

// Global instance of the repository
var (
	employeeRepo *EmployeeRepository
	once         sync.Once
)

// InitEmployeeRepository initializes the employee repository with sample data
func InitEmployeeRepository() *EmployeeRepository {
	once.Do(func() {
		employeeRepo = &EmployeeRepository{
			employees: make(map[string]Employee),
			mutex:     sync.RWMutex{},
		}

		// Add sample data
		sampleEmployees := []Employee{
			{
				ID:         "1",
				FirstName:  "John",
				LastName:   "Doe",
				Email:      "john.doe@example.com",
				Department: "IT",
				Salary:     75000.0,
			},
			{
				ID:         "2",
				FirstName:  "Jane",
				LastName:   "Smith",
				Email:      "jane.smith@example.com",
				Department: "HR",
				Salary:     65000.0,
			},
			{
				ID:         "3",
				FirstName:  "Michael",
				LastName:   "Brown",
				Email:      "michael.brown@example.com",
				Department: "Finance",
				Salary:     85000.0,
			},
			{
				ID:         "4",
				FirstName:  "Sarah",
				LastName:   "Johnson",
				Email:      "sarah.johnson@example.com",
				Department: "Marketing",
				Salary:     70000.0,
			},
			{
				ID:         "5",
				FirstName:  "David",
				LastName:   "Williams",
				Email:      "david.williams@example.com",
				Department: "Operations",
				Salary:     72000.0,
			},
		}

		for _, emp := range sampleEmployees {
			employeeRepo.employees[emp.ID] = emp
		}

		utils.Log.Info("Initialized in-memory employee database with sample data")
	})

	return employeeRepo
}

// GetEmployeeRepository returns the singleton instance of the employee repository
func GetEmployeeRepository() *EmployeeRepository {
	if employeeRepo == nil {
		InitEmployeeRepository()
	}
	return employeeRepo
}

// GetAll returns all employees
func (r *EmployeeRepository) GetAll() []Employee {
	r.mutex.RLock()
	defer r.mutex.RUnlock()

	utils.Log.Debug("Retrieving all employees")
	
	employees := make([]Employee, 0, len(r.employees))
	for _, emp := range r.employees {
		employees = append(employees, emp)
	}
	
	utils.Log.Infof("Retrieved %d employees successfully", len(employees))
	return employees
}

// GetByID returns an employee by ID
func (r *EmployeeRepository) GetByID(id string) (Employee, bool) {
	r.mutex.RLock()
	defer r.mutex.RUnlock()

	utils.Log.Debugf("Looking for employee with ID: %s", id)
	
	employee, exists := r.employees[id]
	
	if !exists {
		utils.Log.Warnf("Employee with ID %s not found", id)
	} else {
		utils.Log.Infof("Found employee with ID: %s", id)
	}
	
	return employee, exists
}

// Create adds a new employee
func (r *EmployeeRepository) Create(employee Employee) Employee {
	r.mutex.Lock()
	defer r.mutex.Unlock()

	utils.Log.Debug("Creating new employee")
	
	// Generate ID if not provided
	if employee.ID == "" {
		employee.ID = uuid.New().String()
	}
	
	r.employees[employee.ID] = employee
	
	utils.Log.Infof("Created employee with ID: %s", employee.ID)
	return employee
}

// Update modifies an existing employee
func (r *EmployeeRepository) Update(id string, employee Employee) (Employee, bool) {
	r.mutex.Lock()
	defer r.mutex.Unlock()

	utils.Log.Debugf("Attempting to update employee with ID: %s", id)
	
	_, exists := r.employees[id]
	if !exists {
		utils.Log.Warnf("Cannot update: employee with ID %s not found", id)
		return Employee{}, false
	}
	
	// Preserve the ID
	employee.ID = id
	r.employees[id] = employee
	
	utils.Log.Infof("Updated employee with ID: %s", id)
	return employee, true
}

// Delete removes an employee
func (r *EmployeeRepository) Delete(id string) bool {
	r.mutex.Lock()
	defer r.mutex.Unlock()

	utils.Log.Debugf("Attempting to delete employee with ID: %s", id)
	
	_, exists := r.employees[id]
	if !exists {
		utils.Log.Warnf("Cannot delete: employee with ID %s not found", id)
		return false
	}
	
	delete(r.employees, id)
	
	utils.Log.Infof("Deleted employee with ID: %s", id)
	return true
}
