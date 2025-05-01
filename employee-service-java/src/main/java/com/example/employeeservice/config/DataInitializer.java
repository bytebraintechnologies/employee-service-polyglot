package com.example.employeeservice.config;

import com.example.employeeservice.model.Employee;
import com.example.employeeservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
            log.info("Preloading sample employee data");
            
            repository.save(new Employee(null, "John", "Doe", "john.doe@example.com", "IT", 75000.0));
            repository.save(new Employee(null, "Jane", "Smith", "jane.smith@example.com", "HR", 65000.0));
            repository.save(new Employee(null, "Michael", "Brown", "michael.brown@example.com", "Finance", 85000.0));
            repository.save(new Employee(null, "Sarah", "Johnson", "sarah.johnson@example.com", "Marketing", 70000.0));
            repository.save(new Employee(null, "David", "Williams", "david.williams@example.com", "Operations", 72000.0));
            
            log.info("Sample data loaded successfully");
        };
    }
}
