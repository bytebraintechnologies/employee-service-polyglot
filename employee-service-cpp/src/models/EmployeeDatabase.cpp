#include "EmployeeDatabase.h"
#include "Logger.h"
#include <random>
#include <sstream>
#include <iomanip>

EmployeeDatabase& EmployeeDatabase::getInstance() {
    static EmployeeDatabase instance;
    return instance;
}

EmployeeDatabase::EmployeeDatabase() {
    initializeSampleData();
}

void EmployeeDatabase::initializeSampleData() {
    // Sample employees
    std::vector<Employee> sampleEmployees = {
        Employee("John", "Doe", "john.doe@example.com", "IT", 75000.0),
        Employee("Jane", "Smith", "jane.smith@example.com", "HR", 65000.0),
        Employee("Michael", "Brown", "michael.brown@example.com", "Finance", 85000.0),
        Employee("Sarah", "Johnson", "sarah.johnson@example.com", "Marketing", 70000.0),
        Employee("David", "Williams", "david.williams@example.com", "Operations", 72000.0)
    };

    // Add to database with IDs
    for (size_t i = 0; i < sampleEmployees.size(); ++i) {
        Employee& employee = sampleEmployees[i];
        std::string id = std::to_string(i + 1); // IDs from 1 to 5
        employee.setId(id);
        employees[id] = employee;
    }

    LOG_INFO("Initialized in-memory employee database with " + std::to_string(employees.size()) + " sample records");
}

std::vector<Employee> EmployeeDatabase::getAll() {
    std::lock_guard<std::mutex> lock(mutex);
    std::vector<Employee> result;
    result.reserve(employees.size());
    
    for (const auto& pair : employees) {
        result.push_back(pair.second);
    }
    
    return result;
}

std::optional<Employee> EmployeeDatabase::getById(const std::string& id) {
    std::lock_guard<std::mutex> lock(mutex);
    
    auto it = employees.find(id);
    if (it != employees.end()) {
        return it->second;
    }
    
    return std::nullopt;
}

Employee EmployeeDatabase::create(Employee employee) {
    std::lock_guard<std::mutex> lock(mutex);
    
    // Generate new ID if not provided
    std::string id;
    if (employee.getId()) {
        id = *employee.getId();
    } else {
        id = generateUuid();
        employee.setId(id);
    }
    
    // Add to database
    employees[id] = employee;
    
    LOG_INFO("Created employee with ID: " + id);
    return employee;
}

std::optional<Employee> EmployeeDatabase::update(const std::string& id, const Employee& employee) {
    std::lock_guard<std::mutex> lock(mutex);
    
    if (employees.find(id) == employees.end()) {
        LOG_WARNING("Employee with ID: " + id + " not found");
        return std::nullopt;
    }
    
    // Create updated employee
    Employee updatedEmployee = employee;
    updatedEmployee.setId(id); // Ensure ID stays the same
    
    // Update in database
    employees[id] = updatedEmployee;
    
    LOG_INFO("Updated employee with ID: " + id);
    return updatedEmployee;
}

bool EmployeeDatabase::remove(const std::string& id) {
    std::lock_guard<std::mutex> lock(mutex);
    
    if (employees.find(id) == employees.end()) {
        LOG_WARNING("Employee with ID: " + id + " not found");
        return false;
    }
    
    employees.erase(id);
    LOG_INFO("Deleted employee with ID: " + id);
    return true;
}

std::string EmployeeDatabase::generateUuid() const {
    // Simple UUID generation - In production, use a proper UUID library
    static std::random_device rd;
    static std::mt19937 gen(rd());
    static std::uniform_int_distribution<> distrib(0, 15);
    
    std::stringstream ss;
    ss << std::hex;
    
    for (int i = 0; i < 32; ++i) {
        ss << distrib(gen);
        if (i == 7 || i == 11 || i == 15 || i == 19) {
            ss << "-";
        }
    }
    
    return ss.str();
}
