package com.example.employeeservice.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        Map<String, Object> details = new HashMap<>();
        details.put("service", "employee-service-java");
        details.put("version", "1.0.0");
        details.put("description", "Employee Service Java Implementation");
        
        // Add system information
        Map<String, Object> system = new HashMap<>();
        system.put("javaVersion", System.getProperty("java.version"));
        system.put("os", System.getProperty("os.name"));
        details.put("system", system);
        
        return Health.up().withDetails(details).build();
    }
}