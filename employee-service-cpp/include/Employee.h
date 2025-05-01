#pragma once

#include <string>
#include <optional>
#include <nlohmann/json.hpp>

using json = nlohmann::json;

/**
 * Employee class representing an employee entity
 */
class Employee {
public:
    // Constructors
    Employee() = default;
    Employee(
        const std::string& firstName,
        const std::string& lastName,
        const std::string& email,
        const std::optional<std::string>& department = std::nullopt,
        double salary = 0.0
    );

    // Getters
    std::optional<std::string> getId() const { return id; }
    std::string getFirstName() const { return firstName; }
    std::string getLastName() const { return lastName; }
    std::string getEmail() const { return email; }
    std::optional<std::string> getDepartment() const { return department; }
    double getSalary() const { return salary; }

    // Setters
    void setId(const std::string& value) { id = value; }
    void setFirstName(const std::string& value) { firstName = value; }
    void setLastName(const std::string& value) { lastName = value; }
    void setEmail(const std::string& value) { email = value; }
    void setDepartment(const std::optional<std::string>& value) { department = value; }
    void setSalary(double value) { salary = value; }

    // Validate email format
    static bool isValidEmail(const std::string& email);

    // JSON conversion
    json toJson() const;
    static Employee fromJson(const json& j);

private:
    std::optional<std::string> id;
    std::string firstName;
    std::string lastName;
    std::string email;
    std::optional<std::string> department;
    double salary = 0.0;
};

// Json serialization helpers
void to_json(json& j, const Employee& employee);
void from_json(const json& j, Employee& employee);
