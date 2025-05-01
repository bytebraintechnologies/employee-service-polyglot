#include "Logger.h"

Logger& Logger::getInstance() {
    static Logger instance;
    return instance;
}

Logger::Logger() {
    initialize();
}

void Logger::initialize() {
    if (initialized) {
        return;
    }

    try {
        // Create logs directory if it doesn't exist
        std::filesystem::path logsDir("logs");
        if (!std::filesystem::exists(logsDir)) {
            std::filesystem::create_directory(logsDir);
        }

        // Open log files
        appLogFile.open("logs/app.log", std::ios::app);
        errorLogFile.open("logs/error.log", std::ios::app);

        if (!appLogFile.is_open() || !errorLogFile.is_open()) {
            std::cerr << "Failed to open log files" << std::endl;
            return;
        }

        initialized = true;
        log(LogLevel::INFO, "Logging system initialized");
    } catch (const std::exception& e) {
        std::cerr << "Error initializing logger: " << e.what() << std::endl;
    }
}

void Logger::log(LogLevel level, const std::string& message) {
    if (!initialized) {
        initialize();
    }

    std::lock_guard<std::mutex> lock(mutex);

    // Format log message
    std::string timestamp = getCurrentTimestamp();
    std::string logLevel = levelToString(level);
    std::string formattedMessage = timestamp + " | " + logLevel + " | " + message;

    // Write to console
    std::cout << formattedMessage << std::endl;

    // Write to app log file
    if (appLogFile.is_open()) {
        appLogFile << formattedMessage << std::endl;
        appLogFile.flush();
    }

    // Write to error log file if error level
    if (level == LogLevel::ERROR && errorLogFile.is_open()) {
        errorLogFile << formattedMessage << std::endl;
        errorLogFile.flush();
    }
}

void Logger::debug(const std::string& message) {
    log(LogLevel::DEBUG, message);
}

void Logger::info(const std::string& message) {
    log(LogLevel::INFO, message);
}

void Logger::warning(const std::string& message) {
    log(LogLevel::WARNING, message);
}

void Logger::error(const std::string& message) {
    log(LogLevel::ERROR, message);
}

std::string Logger::levelToString(LogLevel level) {
    switch (level) {
        case LogLevel::DEBUG:   return "DEBUG   ";
        case LogLevel::INFO:    return "INFO    ";
        case LogLevel::WARNING: return "WARNING ";
        case LogLevel::ERROR:   return "ERROR   ";
        default:                return "UNKNOWN ";
    }
}

std::string Logger::getCurrentTimestamp() {
    auto now = std::chrono::system_clock::now();
    auto time = std::chrono::system_clock::to_time_t(now);
    
    std::stringstream ss;
    ss << std::put_time(std::localtime(&time), "%Y-%m-%d %H:%M:%S");
    
    return ss.str();
}
