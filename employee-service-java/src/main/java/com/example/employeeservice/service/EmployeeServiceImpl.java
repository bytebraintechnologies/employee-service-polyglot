package com.example.employeeservice.service;

import com.example.employeeservice.model.Employee;
import com.example.employeeservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    @Override
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeRepository.findAll();
    }
    
    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        return employeeRepository.findById(id);
    }
    
    @Override
    @Transactional
    public Employee createEmployee(Employee employee) {
        log.info("Creating new employee: {}", employee);
        return employeeRepository.save(employee);
    }
    
    @Override
    @Transactional
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        log.info("Updating employee with ID: {}", id);
        
        Optional<Employee> employee = employeeRepository.findById(id);
        
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            existingEmployee.setFirstName(employeeDetails.getFirstName());
            existingEmployee.setLastName(employeeDetails.getLastName());
            existingEmployee.setEmail(employeeDetails.getEmail());
            existingEmployee.setDepartment(employeeDetails.getDepartment());
            existingEmployee.setSalary(employeeDetails.getSalary());
            
            log.info("Employee updated successfully");
            return employeeRepository.save(existingEmployee);
        } else {
            log.error("Employee with ID: {} not found", id);
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }
    
    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        
        Optional<Employee> employee = employeeRepository.findById(id);
        
        if (employee.isPresent()) {
            employeeRepository.deleteById(id);
            log.info("Employee deleted successfully");
        } else {
            log.error("Employee with ID: {} not found", id);
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }
}
