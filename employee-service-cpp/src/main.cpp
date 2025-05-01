#include <crow.h>
#include <iostream>
#include <string>
#include <cstdlib>
#include "Logger.h"
#include "Router.h"

// Get environment variable or default value
std::string getEnv(const std::string& name, const std::string& defaultValue) {
    const char* value = std::getenv(name.c_str());
    return value ? value : defaultValue;
}

int main() {
    // Initialize logger
    Logger::getInstance().initialize();
    LOG_INFO("Starting Employee Service C++ Application");

    // Get port from environment variables or use default
    std::string port = getEnv("PORT", "8080");
    LOG_INFO("Server will listen on port " + port);

    // Create Crow app
    crow::SimpleApp app;

    // Configure routes
    Router::configureRoutes(app);

    // Start server
    app.port(std::stoi(port))
       .multithreaded()
       .run();

    return 0;
}
