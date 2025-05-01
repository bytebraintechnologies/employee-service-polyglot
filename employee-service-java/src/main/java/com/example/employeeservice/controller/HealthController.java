package com.example.employeeservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "employee-service-java");
        
        // Add system information
        Map<String, String> system = new HashMap<>();
        system.put("javaVersion", System.getProperty("java.version"));
        system.put("os", System.getProperty("os.name"));
        response.put("system", system);
        
        return ResponseEntity.ok(response);
    }
}