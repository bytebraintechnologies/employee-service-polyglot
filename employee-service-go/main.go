package main

import (
	"employee-service/models"
	"employee-service/routes"
	"employee-service/utils"
	"fmt"
	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"os"
)

func main() {
	// Setup logging
	utils.ConfigureLogger()
	utils.Log.Info("Starting Employee Service Go Application")

	// Initialize the employee repository with sample data
	models.InitEmployeeRepository()

	// Set Gin to release mode in production
	// gin.SetMode(gin.ReleaseMode)

	// Create a new Gin router
	router := gin.New()

	// Add middleware
	router.Use(gin.Recovery())
	router.Use(utils.LoggerMiddleware())
	
	// Configure CORS
	router.Use(cors.Default())

	// Register routes
	routes.SetupEmployeeRoutes(router)
	routes.SetupHealthRoutes(router)

	// Add welcome route
	router.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Welcome to Employee Service API",
		})
	})

	// Get the port from the environment or use default
	port := os.Getenv("PORT")
	if port == "" {
		port = "7070"  // Changed default port to 7070
	}

	// Start the server
	utils.Log.Info(fmt.Sprintf("Server running on port %s", port))
	if err := router.Run(":" + port); err != nil {
		utils.Log.Fatal(fmt.Sprintf("Failed to start server: %v", err))
	}
}