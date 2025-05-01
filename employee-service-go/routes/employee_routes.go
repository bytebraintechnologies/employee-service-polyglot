package routes

import (
	"employee-service/controllers"
	"github.com/gin-gonic/gin"
)

// SetupEmployeeRoutes registers all employee-related routes
func SetupEmployeeRoutes(router *gin.Engine) {
	// Create a new employee controller
	employeeController := controllers.NewEmployeeController()

	// Group all employee routes under /api/employees
	employeeRoutes := router.Group("/api/employees")
	{
		// GET /api/employees - Get all employees
		employeeRoutes.GET("", employeeController.GetAllEmployees)
		
		// GET /api/employees/:id - Get an employee by ID
		employeeRoutes.GET("/:id", employeeController.GetEmployeeByID)
		
		// POST /api/employees - Create a new employee
		employeeRoutes.POST("", employeeController.CreateEmployee)
		
		// PUT /api/employees/:id - Update an employee
		employeeRoutes.PUT("/:id", employeeController.UpdateEmployee)
		
		// DELETE /api/employees/:id - Delete an employee
		employeeRoutes.DELETE("/:id", employeeController.DeleteEmployee)
	}
}
