#pragma once

#include <crow.h>
#include "EmployeeDatabase.h"

/**
 * Controller for handling employee-related HTTP requests
 */
class EmployeeController {
public:
    // Get all employees
    static crow::response getAllEmployees();

    // Get employee by ID
    static crow::response getEmployeeById(const std::string& id);

    // Create a new employee
    static crow::response createEmployee(const crow::request& req);

    // Update an employee
    static crow::response updateEmployee(const std::string& id, const crow::request& req);

    // Delete an employee
    static crow::response deleteEmployee(const std::string& id);
};
