package com.example.employeeservice.service;

import com.example.employeeservice.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    
    List<Employee> getAllEmployees();
    
    Optional<Employee> getEmployeeById(Long id);
    
    Employee createEmployee(Employee employee);
    
    Employee updateEmployee(Long id, Employee employeeDetails);
    
    void deleteEmployee(Long id);
}
