#include "Router.h"
#include "Logger.h"
#include <nlohmann/json.hpp>

// Use json namespace for convenience
using json = nlohmann::json;
#include <chrono>
#include <string>
#include <map>
#include <unordered_map>

#ifdef _WIN32
#include <windows.h>
#include <psapi.h>
#elif defined(__APPLE__)
#include <unistd.h>
#include <sys/types.h>
#include <sys/param.h>
#include <sys/sysctl.h>
#include <sys/utsname.h>
#elif defined(__unix__) || defined(__unix) || defined(unix) || defined(__linux__)
#include <unistd.h>
#include <sys/types.h>
#include <sys/param.h>
#include <sys/utsname.h>
#endif

template<typename... Middlewares>
void Router::configureRoutes(crow::Crow<Middlewares...>& app) {
    // CORS middleware is not available in this build
    // We'll add CORS headers manually in each response

    // Home route
    CROW_ROUTE(app, "/")
    ([](const crow::request& req, crow::response& res) {
        json response = {
            {"message", "Welcome to Employee Service API"}
        };
        res = crow::response(200, response.dump());
        res.add_header("Access-Control-Allow-Origin", "*");
        res.add_header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.add_header("Access-Control-Allow-Headers", "Content-Type");
    });

    // Configure employee routes
    configureEmployeeRoutes(app);

    // Configure health routes
    configureHealthRoutes(app);

    // Add a global middleware for CORS headers
    LOG_INFO("Setting up CORS middleware");
    
    // Since we can't use after_handle in this version of Crow,
    // we'll need to add CORS headers to each endpoint

    // Add OPTIONS method handling for CORS preflight requests
    app.route_dynamic("/(.*)").methods("OPTIONS"_method)
    ([](const crow::request& req, crow::response& res) {
        res = crow::response(204);
        res.add_header("Access-Control-Allow-Origin", "*");
        res.add_header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.add_header("Access-Control-Allow-Headers", "Content-Type");
    });

    LOG_INFO("Routes configured successfully");
}

template<typename... Middlewares>
void Router::configureEmployeeRoutes(crow::Crow<Middlewares...>& app) {
    // Log all employee routes requests
    LOG_INFO("Setting up employee routes");

    // GET /api/employees - Get all employees
    CROW_ROUTE(app, "/api/employees")
        .methods("GET"_method)
        ([](const crow::request& req, crow::response& res) {
            res = EmployeeController::getAllEmployees();
        });

    // GET /api/employees/:id - Get employee by ID
    CROW_ROUTE(app, "/api/employees/<string>")
        .methods("GET"_method)
        ([](const crow::request& req, crow::response& res, const std::string& id) {
            res = EmployeeController::getEmployeeById(id);
        });

    // POST /api/employees - Create a new employee
    CROW_ROUTE(app, "/api/employees")
        .methods("POST"_method)
        ([](const crow::request& req, crow::response& res) {
            res = EmployeeController::createEmployee(req);
        });

    // PUT /api/employees/:id - Update an employee
    CROW_ROUTE(app, "/api/employees/<string>")
        .methods("PUT"_method)
        ([](const crow::request& req, crow::response& res, const std::string& id) {
            res = EmployeeController::updateEmployee(id, req);
        });

    // DELETE /api/employees/:id - Delete an employee
    CROW_ROUTE(app, "/api/employees/<string>")
        .methods("DELETE"_method)
        ([](const crow::request& req, crow::response& res, const std::string& id) {
            res = EmployeeController::deleteEmployee(id);
        });

    LOG_INFO("Employee routes configured");
}

template<typename... Middlewares>
void Router::configureHealthRoutes(crow::Crow<Middlewares...>& app) {
    // GET /health - Health check endpoint
    CROW_ROUTE(app, "/health")
        .methods("GET"_method)
        ([](const crow::request& req, crow::response& res) {
            LOG_INFO("Health check requested");
            
            // Get current timestamp
            auto now = std::chrono::system_clock::now();
            auto now_time_t = std::chrono::system_clock::to_time_t(now);
            std::string timestamp;
            try {
                std::tm now_tm;
#ifdef _WIN32
                localtime_s(&now_tm, &now_time_t);
#else
                localtime_r(&now_time_t, &now_tm);
#endif
                char buffer[80];
                std::strftime(buffer, sizeof(buffer), "%Y-%m-%dT%H:%M:%S%z", &now_tm);
                timestamp = std::string(buffer);
            } catch(...) {
                timestamp = std::to_string(now_time_t);
            }
            
            // Collect system information
            nlohmann::json system_info;
#ifdef _WIN32
            system_info["os"] = "Windows";
            OSVERSIONINFO osvi;
            ZeroMemory(&osvi, sizeof(OSVERSIONINFO));
            osvi.dwOSVersionInfoSize = sizeof(OSVERSIONINFO);
            GetVersionEx(&osvi);
            system_info["version"] = std::to_string(osvi.dwMajorVersion) + "." + std::to_string(osvi.dwMinorVersion);
#elif defined(__APPLE__)
            system_info["os"] = "macOS";
            char str[256];
            size_t size = sizeof(str);
            sysctlbyname("kern.osrelease", str, &size, NULL, 0);
            system_info["version"] = str;
#else
            struct utsname buffer;
            if (uname(&buffer) == 0) {
                system_info["os"] = buffer.sysname;
                system_info["version"] = buffer.release;
            } else {
                system_info["os"] = "Linux/Unix";
                system_info["version"] = "Unknown";
            }
#endif
            system_info["cpp_standard"] = std::to_string(__cplusplus);
            
            // Collect resource information
            nlohmann::json resources;
            resources["pid"] = getpid();
            
#ifdef _WIN32
            PROCESS_MEMORY_COUNTERS_EX pmc;
            if (GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc))) {
                resources["working_set_size"] = pmc.WorkingSetSize;
                resources["private_usage"] = pmc.PrivateUsage;
            }
            
            SYSTEM_INFO sysInfo;
            GetSystemInfo(&sysInfo);
            resources["processor_count"] = sysInfo.dwNumberOfProcessors;
#else
            // For Unix/Linux/Mac we could add more system info here
            resources["processor_count"] = sysconf(_SC_NPROCESSORS_ONLN);
#endif
            
            // Build the full response
            nlohmann::json response = {
                {"status", "UP"},
                {"service", "employee-service-cpp"},
                {"time", timestamp},
                {"system", system_info},
                {"resources", resources}
            };
            
            res = crow::response(200, response.dump(4));
            res.add_header("Access-Control-Allow-Origin", "*");
            res.add_header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.add_header("Access-Control-Allow-Headers", "Content-Type");
        });
        
    LOG_INFO("Health routes configured");
}

// Explicit template instantiations
template void Router::configureRoutes<>(crow::Crow<>& app);
template void Router::configureEmployeeRoutes<>(crow::Crow<>& app);
template void Router::configureHealthRoutes<>(crow::Crow<>& app);