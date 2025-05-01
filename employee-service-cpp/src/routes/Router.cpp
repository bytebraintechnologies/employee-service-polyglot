#include "Router.h"
#include "Logger.h"
#include <chrono>

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
