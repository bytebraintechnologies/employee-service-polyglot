#pragma once

#include "Employee.h"
#include <unordered_map>
#include <vector>
#include <string>
#include <memory>
#include <mutex>

/**
 * In-memory database for Employee records
 */
class EmployeeDatabase {
public:
    // Get singleton instance
    static EmployeeDatabase& getInstance();

    // Get all employees
    std::vector<Employee> getAll();

    // Get employee by ID
    std::optional<Employee> getById(const std::string& id);

    // Create a new employee
    Employee create(Employee employee);

    // Update an existing employee
    std::optional<Employee> update(const std::string& id, const Employee& employee);

    // Delete an employee
    bool remove(const std::string& id);

private:
    // Private constructor for singleton pattern
    EmployeeDatabase();

    // Delete copy constructor and assignment operator
    EmployeeDatabase(const EmployeeDatabase&) = delete;
    EmployeeDatabase& operator=(const EmployeeDatabase&) = delete;

    // Initialize with sample data
    void initializeSampleData();

    // Generate UUID
    std::string generateUuid() const;

    // In-memory storage
    std::unordered_map<std::string, Employee> employees;
    
    // Thread safety
    std::mutex mutex;
};
