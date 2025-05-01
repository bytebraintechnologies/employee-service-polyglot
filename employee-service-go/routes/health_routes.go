package routes

import (
	"employee-service/utils"
	"github.com/gin-gonic/gin"
	"runtime"
	"time"
)

// SetupHealthRoutes registers health check routes
func SetupHealthRoutes(router *gin.Engine) {
	router.GET("/health", healthCheck)
}

// healthCheck handles the health check endpoint
func healthCheck(c *gin.Context) {
	utils.Log.Info("Health check requested")

	var m runtime.MemStats
	runtime.ReadMemStats(&m)

	// Build response
	healthData := gin.H{
		"status":  "UP",
		"service": "employee-service-go",
		"time":    time.Now().Format(time.RFC3339),
		"system": gin.H{
			"go_version": runtime.Version(),
			"os":         runtime.GOOS,
			"arch":       runtime.GOARCH,
			"cpu_count":  runtime.NumCPU(),
		},
		"resources": gin.H{
			"goroutines":    runtime.NumGoroutine(),
			"alloc_bytes":   m.Alloc,
			"total_alloc":   m.TotalAlloc,
			"heap_objects":  m.HeapObjects,
			"gc_cycles":     m.NumGC,
			"process_start": time.Now().Sub(time.Unix(0, int64(m.LastGC))).String(),
		},
	}

	c.JSON(200, healthData)
}