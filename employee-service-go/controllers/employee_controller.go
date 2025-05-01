package controllers

import (
	"employee-service/models"
	"employee-service/utils"
	"github.com/gin-gonic/gin"
	"net/http"
)

// EmployeeController handles employee-related HTTP requests
type EmployeeController struct {
	repo *models.EmployeeRepository
}

// NewEmployeeController creates a new employee controller
func NewEmployeeController() *EmployeeController {
	return &EmployeeController{
		repo: models.GetEmployeeRepository(),
	}
}

// GetAllEmployees returns all employees
func (c *EmployeeController) GetAllEmployees(ctx *gin.Context) {
	utils.Log.Info("Request to get all employees")
	
	employees := c.repo.GetAll()
	
	utils.Log.Infof("Returning %d employees", len(employees))
	ctx.JSON(http.StatusOK, employees)
}

// GetEmployeeByID returns an employee by ID
func (c *EmployeeController) GetEmployeeByID(ctx *gin.Context) {
	id := ctx.Param("id")
	utils.Log.Infof("Request to get employee with ID: %s", id)
	
	employee, exists := c.repo.GetByID(id)
	if !exists {
		utils.Log.Warnf("Employee with ID %s not found", id)
		ctx.JSON(http.StatusNotFound, gin.H{"message": "Employee not found"})
		return
	}
	
	utils.Log.Infof("Returning employee with ID: %s", id)
	ctx.JSON(http.StatusOK, employee)
}

// CreateEmployee adds a new employee
func (c *EmployeeController) CreateEmployee(ctx *gin.Context) {
	utils.Log.Info("Request to create a new employee")
	
	var employee models.Employee
	if err := ctx.ShouldBindJSON(&employee); err != nil {
		utils.Log.Warnf("Invalid request data: %v", err)
		ctx.JSON(http.StatusBadRequest, gin.H{"message": "Invalid request data", "error": err.Error()})
		return
	}
	
	// ID will be generated in the repository if empty
	createdEmployee := c.repo.Create(employee)
	
	utils.Log.Infof("Created employee with ID: %s", createdEmployee.ID)
	ctx.JSON(http.StatusCreated, createdEmployee)
}

// UpdateEmployee modifies an existing employee
func (c *EmployeeController) UpdateEmployee(ctx *gin.Context) {
	id := ctx.Param("id")
	utils.Log.Infof("Request to update employee with ID: %s", id)
	
	var employee models.Employee
	if err := ctx.ShouldBindJSON(&employee); err != nil {
		utils.Log.Warnf("Invalid request data: %v", err)
		ctx.JSON(http.StatusBadRequest, gin.H{"message": "Invalid request data", "error": err.Error()})
		return
	}
	
	updatedEmployee, exists := c.repo.Update(id, employee)
	if !exists {
		utils.Log.Warnf("Employee with ID %s not found for update", id)
		ctx.JSON(http.StatusNotFound, gin.H{"message": "Employee not found"})
		return
	}
	
	utils.Log.Infof("Updated employee with ID: %s", id)
	ctx.JSON(http.StatusOK, updatedEmployee)
}

// DeleteEmployee removes an employee
func (c *EmployeeController) DeleteEmployee(ctx *gin.Context) {
	id := ctx.Param("id")
	utils.Log.Infof("Request to delete employee with ID: %s", id)
	
	success := c.repo.Delete(id)
	if !success {
		utils.Log.Warnf("Employee with ID %s not found for deletion", id)
		ctx.JSON(http.StatusNotFound, gin.H{"message": "Employee not found"})
		return
	}
	
	utils.Log.Infof("Deleted employee with ID: %s", id)
	ctx.Status(http.StatusNoContent)
}
