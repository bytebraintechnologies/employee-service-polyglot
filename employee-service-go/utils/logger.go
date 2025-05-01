package utils

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/sirupsen/logrus"
	"io"
	"os"
	"path/filepath"
	"time"
)

// Log is the global logger instance
var Log *logrus.Logger

// ConfigureLogger sets up the application logger with file and console output
func ConfigureLogger() {
	// Create logs directory if it doesn't exist
	logsDir := filepath.Join(".", "logs")
	if _, err := os.Stat(logsDir); os.IsNotExist(err) {
		os.Mkdir(logsDir, 0755)
	}

	// Initialize global logger
	Log = logrus.New()

	// Set log format
	Log.SetFormatter(&logrus.TextFormatter{
		FullTimestamp:   true,
		TimestampFormat: "2006-01-02 15:04:05",
	})

	// Set log level
	Log.SetLevel(logrus.InfoLevel)

	// Create log file with date
	currentTime := time.Now()
	logFileName := filepath.Join(logsDir, fmt.Sprintf("app_%s.log", currentTime.Format("2006-01-02")))
	logFile, err := os.OpenFile(logFileName, os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0666)
	if err != nil {
		fmt.Printf("Failed to open log file: %v\n", err)
		return
	}

	// Create a multi-writer for both file and console output
	multiWriter := io.MultiWriter(os.Stdout, logFile)
	Log.SetOutput(multiWriter)

	Log.Info("Logger initialized successfully")
}

// LoggerMiddleware returns a Gin middleware for logging HTTP requests
func LoggerMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		// Start timer
		startTime := time.Now()
		
		// Process request
		c.Next()
		
		// Calculate request processing time
		latency := time.Since(startTime)
		
		// Get request details
		clientIP := c.ClientIP()
		method := c.Request.Method
		path := c.Request.URL.Path
		statusCode := c.Writer.Status()
		
		// Log request details
		if statusCode >= 400 {
			Log.WithFields(logrus.Fields{
				"status":     statusCode,
				"latency":    latency,
				"client_ip":  clientIP,
				"method":     method,
				"path":       path,
				"error":      c.Errors.String(),
			}).Error("Request failed")
		} else {
			Log.WithFields(logrus.Fields{
				"status":     statusCode,
				"latency":    latency,
				"client_ip":  clientIP,
				"method":     method,
				"path":       path,
			}).Info("Request processed")
		}
	}
}
