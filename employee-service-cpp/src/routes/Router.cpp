#include "Router.h"
#include "Logger.h"
#include <chrono>
#include <string>
#include <map>
#include <unordered_map>

#ifdef _WIN32
#include <windows.h>
#include <psapi.h>
#elif defined(__unix__) || defined(__unix) || defined(unix) || defined(__APPLE__)
#include <unistd.h>
#include <sys/types.h>
#include <sys/param.h>
#include <sys/sysctl.h>
#include <sys/utsname.h>
#endif

void Router::configureRoutes(crow::App<crow::CORSHandler>& app) {
    // Configure CORS
    auto& cors = app.get_middleware<crow::CORSHandler>();
    cors
        .global()
        .headers("Content-Type")
        .methods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allow_credentials();

    // Home route
    CROW_ROUTE(app, "/")
    ([]() {
        json response = {
            {"message", "Welcome to Employee Service API"}
        };
        return crow::response(200, response.dump());
    });

    // Configure employee routes
    configureEmployeeRoutes(app);

    // Configure health routes
    configureHealthRoutes(app);

    // Logging middleware
    app.after_handle([](crow::request& req, crow::response& res) {
        auto now = std::chrono::system_clock::now();
        auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(now - req.get_start_time());
        LOG_INFO("Response: " + std::to_string(res.code) + " - took " + std::to_string(duration.count()) + "ms");
    });

    LOG_INFO("Routes configured successfully");
}

void Router::configureEmployeeRoutes(crow::App<crow::CORSHandler>& app) {
    // Logging middleware for employee routes
    app.route_dynamic("/api/employees")
       .before([](crow::request& req) {
           req.set_start_time(std::chrono::system_clock::now());
           LOG_INFO("Request: " + std::string(req.method) + " " + req.url);
           return true;
       });

    // GET /api/employees - Get all employees
    CROW_ROUTE(app, "/api/employees")
        .methods("GET"_method)
        ([]() {
            return EmployeeController::getAllEmployees();
        });

    // GET /api/employees/:id - Get employee by ID
    CROW_ROUTE(app, "/api/employees/<string>")
        .methods("GET"_method)
        ([](const std::string& id) {
            return EmployeeController::getEmployeeById(id);
        });

    // POST /api/employees - Create a new employee
    CROW_ROUTE(app, "/api/employees")
        .methods("POST"_method)
        ([](const crow::request& req) {
            return EmployeeController::createEmployee(req);
        });

    // PUT /api/employees/:id - Update an employee
    CROW_ROUTE(app, "/api/employees/<string>")
        .methods("PUT"_method)
        ([](const std::string& id, const crow::request& req) {
            return EmployeeController::updateEmployee(id, req);
        });

    // DELETE /api/employees/:id - Delete an employee
    CROW_ROUTE(app, "/api/employees/<string>")
        .methods("DELETE"_method)
        ([](const std::string& id) {
            return EmployeeController::deleteEmployee(id);
        });

    LOG_INFO("Employee routes configured");
}

void Router::configureHealthRoutes(crow::App<crow::CORSHandler>& app) {
    // GET /health - Health check endpoint
    CROW_ROUTE(app, "/health")
        .methods("GET"_method)
        ([]() {
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
            
            return crow::response(200, response.dump(4));
        });
        
    LOG_INFO("Health routes configured");
