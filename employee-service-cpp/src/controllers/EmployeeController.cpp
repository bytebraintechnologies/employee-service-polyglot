#include "EmployeeController.h"
#include "Logger.h"
#include <nlohmann/json.hpp>

using json = nlohmann::json;

crow::response EmployeeController::getAllEmployees() {
    LOG_INFO("Request to get all employees");
    
    try {
        auto employees = EmployeeDatabase::getInstance().getAll();
        
        // Convert to JSON array
        json j = json::array();
        for (const auto& employee : employees) {
            j.push_back(employee.toJson());
        }
        
        LOG_INFO("Retrieved " + std::to_string(employees.size()) + " employees successfully");
        return crow::response(200, j.dump());
    } catch (const std::exception& e) {
        LOG_ERROR("Error retrieving all employees: " + std::string(e.what()));
        
        json error = {
            {"message", "Error retrieving employees"},
            {"error", e.what()}
        };
        
        return crow::response(500, error.dump());
    }
}

crow::response EmployeeController::getEmployeeById(const std::string& id) {
    LOG_INFO("Request to get employee with ID: " + id);
    
    try {
        auto employee = EmployeeDatabase::getInstance().getById(id);
        
        if (!employee) {
            LOG_WARNING("Employee with ID: " + id + " not found");
            
            json error = {
                {"message", "Employee with ID: " + id + " not found"}
            };
            
            return crow::response(404, error.dump());
        }
        
        LOG_INFO("Retrieved employee with ID: " + id + " successfully");
        return crow::response(200, employee->toJson().dump());
    } catch (const std::exception& e) {
        LOG_ERROR("Error retrieving employee with ID " + id + ": " + std::string(e.what()));
        
        json error = {
            {"message", "Error retrieving employee"},
            {"error", e.what()}
        };
        
        return crow::response(500, error.dump());
    }
}

crow::response EmployeeController::createEmployee(const crow::request& req) {
    LOG_INFO("Request to create a new employee");
    
    try {
        // Parse request body
        json body = json::parse(req.body);
        LOG_DEBUG("Request data: " + body.dump());
        
        // Validate input data
        Employee employee = Employee::fromJson(body);
        
        if (!Employee::isValidEmail(employee.getEmail())) {
            LOG_WARNING("Invalid email format: " + employee.getEmail());
            
            json error = {
                {"message", "Invalid employee data"},
                {"error", "Invalid email format"}
            };
            
            return crow::response(400, error.dump());
        }
        
        // Create employee
        auto newEmployee = EmployeeDatabase::getInstance().create(employee);
        
        LOG_INFO("Employee created successfully with ID: " + *newEmployee.getId());
        return crow::response(201, newEmployee.toJson().dump());
    } catch (const json::exception& e) {
        LOG_ERROR("Error parsing JSON: " + std::string(e.what()));
        
        json error = {
            {"message", "Invalid JSON data"},
            {"error", e.what()}
        };
        
        return crow::response(400, error.dump());
    } catch (const std::exception& e) {
        LOG_ERROR("Error creating employee: " + std::string(e.what()));
        
        json error = {
            {"message", "Error creating employee"},
            {"error", e.what()}
        };
        
        return crow::response(500, error.dump());
    }
}

crow::response EmployeeController::updateEmployee(const std::string& id, const crow::request& req) {
    LOG_INFO("Request to update employee with ID: " + id);
    
    try {
        // Parse request body
        json body = json::parse(req.body);
        LOG_DEBUG("Request data: " + body.dump());
        
        // Validate input data
        Employee employee = Employee::fromJson(body);
        
        if (!Employee::isValidEmail(employee.getEmail())) {
            LOG_WARNING("Invalid email format: " + employee.getEmail());
            
            json error = {
                {"message", "Invalid employee data"},
                {"error", "Invalid email format"}
            };
            
            return crow::response(400, error.dump());
        }
        
        // Update employee
        auto updatedEmployee = EmployeeDatabase::getInstance().update(id, employee);
        
        if (!updatedEmployee) {
            LOG_WARNING("Employee with ID: " + id + " not found");
            
            json error = {
                {"message", "Employee with ID: " + id + " not found"}
            };
            
            return crow::response(404, error.dump());
        }
        
        LOG_INFO("Employee with ID: " + id + " updated successfully");
        return crow::response(200, updatedEmployee->toJson().dump());
    } catch (const json::exception& e) {
        LOG_ERROR("Error parsing JSON: " + std::string(e.what()));
        
        json error = {
            {"message", "Invalid JSON data"},
            {"error", e.what()}
        };
        
        return crow::response(400, error.dump());
    } catch (const std::exception& e) {
        LOG_ERROR("Error updating employee with ID " + id + ": " + std::string(e.what()));
        
        json error = {
            {"message", "Error updating employee"},
            {"error", e.what()}
        };
        
        return crow::response(500, error.dump());
    }
}

crow::response EmployeeController::deleteEmployee(const std::string& id) {
    LOG_INFO("Request to delete employee with ID: " + id);
    
    try {
        bool result = EmployeeDatabase::getInstance().remove(id);
        
        if (!result) {
            LOG_WARNING("Employee with ID: " + id + " not found");
            
            json error = {
                {"message", "Employee with ID: " + id + " not found"}
            };
            
            return crow::response(404, error.dump());
        }
        
        LOG_INFO("Employee with ID: " + id + " deleted successfully");
        return crow::response(204);
    } catch (const std::exception& e) {
        LOG_ERROR("Error deleting employee with ID " + id + ": " + std::string(e.what()));
        
        json error = {
            {"message", "Error deleting employee"},
            {"error", e.what()}
        };
        
        return crow::response(500, error.dump());
    }
}
