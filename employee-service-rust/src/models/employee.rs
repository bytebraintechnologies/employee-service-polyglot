use serde::{Deserialize, Serialize};
use std::collections::HashMap;
use std::sync::{Arc, Mutex};
use tracing::{debug, info, warn};
use uuid::Uuid;
use validator::Validate;

// Employee schema for data validation and serialization
#[derive(Debug, Serialize, Deserialize, Clone, Validate)]
pub struct Employee {
    #[serde(skip_serializing_if = "Option::is_none")]
    pub id: Option<String>,
    pub first_name: String,
    pub last_name: String,
    #[validate(email)]
    pub email: String,
    #[serde(skip_serializing_if = "Option::is_none")]
    pub department: Option<String>,
    #[serde(default)]
    pub salary: f64,
}

// In-memory database for employees
#[derive(Debug, Clone)]
pub struct EmployeeDatabase {
    employees: Arc<Mutex<HashMap<String, Employee>>>,
}

impl EmployeeDatabase {
    pub fn new() -> Self {
        let employees = Arc::new(Mutex::new(HashMap::new()));
        let instance = Self { employees };
        instance.initialize_sample_data();
        instance
    }

    fn initialize_sample_data(&self) {
        let sample_employees = vec![
            Employee {
                id: Some("1".to_string()),
                first_name: "John".to_string(),
                last_name: "Doe".to_string(),
                email: "john.doe@example.com".to_string(),
                department: Some("IT".to_string()),
                salary: 75000.0,
            },
            Employee {
                id: Some("2".to_string()),
                first_name: "Jane".to_string(),
                last_name: "Smith".to_string(),
                email: "jane.smith@example.com".to_string(),
                department: Some("HR".to_string()),
                salary: 65000.0,
            },
            Employee {
                id: Some("3".to_string()),
                first_name: "Michael".to_string(),
                last_name: "Brown".to_string(),
                email: "michael.brown@example.com".to_string(),
                department: Some("Finance".to_string()),
                salary: 85000.0,
            },
            Employee {
                id: Some("4".to_string()),
                first_name: "Sarah".to_string(),
                last_name: "Johnson".to_string(),
                email: "sarah.johnson@example.com".to_string(),
                department: Some("Marketing".to_string()),
                salary: 70000.0,
            },
            Employee {
                id: Some("5".to_string()),
                first_name: "David".to_string(),
                last_name: "Williams".to_string(),
                email: "david.williams@example.com".to_string(),
                department: Some("Operations".to_string()),
                salary: 72000.0,
            },
        ];

        let mut employees = self.employees.lock().unwrap();
        for employee in sample_employees {
            if let Some(id) = &employee.id {
                employees.insert(id.clone(), employee);
            }
        }

        info!(
            "Initialized in-memory employee database with {} sample records",
            employees.len()
        );
    }

    pub fn get_all(&self) -> Vec<Employee> {
        let employees = self.employees.lock().unwrap();
        employees.values().cloned().collect()
    }

    pub fn get_by_id(&self, id: &str) -> Option<Employee> {
        let employees = self.employees.lock().unwrap();
        employees.get(id).cloned()
    }

    pub fn create(&self, mut employee: Employee) -> Employee {
        let mut employees = self.employees.lock().unwrap();
        
        // Generate new ID if not provided
        let id = match employee.id {
            Some(ref id) if !id.is_empty() => id.clone(),
            _ => Uuid::new_v4().to_string(),
        };
        
        employee.id = Some(id.clone());
        employees.insert(id, employee.clone());
        
        info!("Created employee with ID: {}", employee.id.as_ref().unwrap());
        employee
    }

    pub fn update(&self, id: &str, updated_employee: Employee) -> Option<Employee> {
        let mut employees = self.employees.lock().unwrap();
        
        if !employees.contains_key(id) {
            warn!("Employee with ID: {} not found", id);
            return None;
        }
        
        let mut employee_to_update = updated_employee.clone();
        employee_to_update.id = Some(id.to_string());
        
        employees.insert(id.to_string(), employee_to_update.clone());
        
        info!("Updated employee with ID: {}", id);
        Some(employee_to_update)
    }

    pub fn delete(&self, id: &str) -> bool {
        let mut employees = self.employees.lock().unwrap();
        
        if !employees.contains_key(id) {
            warn!("Employee with ID: {} not found", id);
            return false;
        }
        
        employees.remove(id);
        info!("Deleted employee with ID: {}", id);
        true
    }
}

// Singleton instance of the employee database
lazy_static::lazy_static! {
    pub static ref EMPLOYEE_DB: EmployeeDatabase = EmployeeDatabase::new();
}
