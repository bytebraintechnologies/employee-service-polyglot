#include "Employee.h"
#include <regex>

Employee::Employee(
    const std::string& firstName,
    const std::string& lastName,
    const std::string& email,
    const std::optional<std::string>& department,
    double salary
) : firstName(firstName), lastName(lastName), email(email), department(department), salary(salary) {}

bool Employee::isValidEmail(const std::string& email) {
    // Basic email validation using regex
    const std::regex pattern(R"([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,})");
    return std::regex_match(email, pattern);
}

json Employee::toJson() const {
    json j;
    to_json(j, *this);
    return j;
}

Employee Employee::fromJson(const json& j) {
    Employee employee;
    from_json(j, employee);
    return employee;
}

void to_json(json& j, const Employee& employee) {
    j = json{
        {"firstName", employee.getFirstName()},
        {"lastName", employee.getLastName()},
        {"email", employee.getEmail()},
        {"salary", employee.getSalary()}
    };
    
    // Add optional fields if they exist
    if (employee.getId()) {
        j["id"] = *employee.getId();
    }
    
    if (employee.getDepartment()) {
        j["department"] = *employee.getDepartment();
    }
}

void from_json(const json& j, Employee& employee) {
    // Required fields
    employee.setFirstName(j.at("firstName").get<std::string>());
    employee.setLastName(j.at("lastName").get<std::string>());
    employee.setEmail(j.at("email").get<std::string>());
    
    // Optional fields
    if (j.contains("id")) {
        employee.setId(j.at("id").get<std::string>());
    }
    
    if (j.contains("department")) {
        employee.setDepartment(j.at("department").get<std::string>());
    } else {
        employee.setDepartment(std::nullopt);
    }
    
    if (j.contains("salary")) {
        employee.setSalary(j.at("salary").get<double>());
    } else {
        employee.setSalary(0.0);
    }
}
