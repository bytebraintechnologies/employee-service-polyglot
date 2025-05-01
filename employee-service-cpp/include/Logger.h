#pragma once

#include <string>
#include <fstream>
#include <mutex>
#include <iostream>
#include <filesystem>
#include <chrono>
#include <iomanip>
#include <sstream>

enum class LogLevel {
    DEBUG,
    INFO,
    WARNING,
    ERROR
};

/**
 * Thread-safe logging utility
 */
class Logger {
public:
    // Get singleton instance
    static Logger& getInstance();

    // Log a message with the specified level
    void log(LogLevel level, const std::string& message);

    // Convenience methods for different log levels
    void debug(const std::string& message);
    void info(const std::string& message);
    void warning(const std::string& message);
    void error(const std::string& message);

    // Initialize the logger
    void initialize();

private:
    // Private constructor for singleton pattern
    Logger();
    
    // Delete copy constructor and assignment operator
    Logger(const Logger&) = delete;
    Logger& operator=(const Logger&) = delete;

    // Convert log level to string
    std::string levelToString(LogLevel level);

    // Get current timestamp
    std::string getCurrentTimestamp();

    // File streams
    std::ofstream appLogFile;
    std::ofstream errorLogFile;

    // Thread safety
    std::mutex mutex;

    // Initialized flag
    bool initialized = false;
};

// Macros for easier logging
#define LOG_DEBUG(message) Logger::getInstance().debug(message)
#define LOG_INFO(message) Logger::getInstance().info(message)
#define LOG_WARNING(message) Logger::getInstance().warning(message)
#define LOG_ERROR(message) Logger::getInstance().error(message)
