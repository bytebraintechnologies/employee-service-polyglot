package com.example.employeeservice.health;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Custom health indicator for the Employee Service
 */
@Component
public class EmployeeServiceHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        
        // Get system information
        Map<String, Object> details = new HashMap<>();
        details.put("service", "employee-service-java");
        details.put("time", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        
        // System information
        Map<String, Object> system = new HashMap<>();
        system.put("os", System.getProperty("os.name"));
        system.put("version", System.getProperty("os.version"));
        system.put("java_version", System.getProperty("java.version"));
        system.put("available_processors", osBean.getAvailableProcessors());
        details.put("system", system);
        
        // Resource information
        Map<String, Object> resources = new HashMap<>();
        resources.put("system_load_average", osBean.getSystemLoadAverage());
        resources.put("heap_memory_used", memoryBean.getHeapMemoryUsage().getUsed());
        resources.put("heap_memory_max", memoryBean.getHeapMemoryUsage().getMax());
        resources.put("non_heap_memory_used", memoryBean.getNonHeapMemoryUsage().getUsed());
        details.put("resources", resources);
        
        return Health.up().withDetails(details).build();
    }
}