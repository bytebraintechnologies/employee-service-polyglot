use actix_web::{delete, get, post, put, web, HttpResponse, Responder};
use serde_json::json;
use tracing::{error, info, warn};
use validator::Validate;

use crate::models::employee::{Employee, EMPLOYEE_DB};

// Get all employees
#[get("")]
pub async fn get_all_employees() -> impl Responder {
    info!("Request to get all employees");
    
    match EMPLOYEE_DB.get_all() {
        employees => {
            info!("Retrieved {} employees successfully", employees.len());
            HttpResponse::Ok().json(employees)
        }
    }
}

// Get employee by ID
#[get("/{id}")]
pub async fn get_employee_by_id(path: web::Path<String>) -> impl Responder {
    let employee_id = path.into_inner();
    info!("Request to get employee with ID: {}", employee_id);
    
    match EMPLOYEE_DB.get_by_id(&employee_id) {
        Some(employee) => {
            info!("Retrieved employee with ID: {} successfully", employee_id);
            HttpResponse::Ok().json(employee)
        },
        None => {
            warn!("Employee with ID: {} not found", employee_id);
            HttpResponse::NotFound().json(json!({
                "message": format!("Employee with ID: {} not found", employee_id)
            }))
        }
    }
}

// Create a new employee
#[post("")]
pub async fn create_employee(employee: web::Json<Employee>) -> impl Responder {
    info!("Request to create a new employee");
    
    // Validate input data
    if let Err(e) = employee.validate() {
        warn!("Invalid employee data: {:?}", e);
        return HttpResponse::BadRequest().json(json!({
            "message": "Invalid employee data",
            "error": format!("{:?}", e)
        }));
    }
    
    // Create employee in database
    let new_employee = EMPLOYEE_DB.create(employee.into_inner());
    info!("Employee created successfully with ID: {}", new_employee.id.as_ref().unwrap());
    
    HttpResponse::Created().json(new_employee)
}

// Update an employee
#[put("/{id}")]
pub async fn update_employee(path: web::Path<String>, employee: web::Json<Employee>) -> impl Responder {
    let employee_id = path.into_inner();
    info!("Request to update employee with ID: {}", employee_id);
    
    match EMPLOYEE_DB.update(&employee_id, employee.into_inner()) {
        Some(updated_employee) => {
            info!("Employee with ID: {} updated successfully", employee_id);
            HttpResponse::Ok().json(updated_employee)
        },
        None => {
            warn!("Employee with ID: {} not found", employee_id);
            HttpResponse::NotFound().json(json!({
                "message": format!("Employee with ID: {} not found", employee_id)
            }))
        }
    }
}

// Delete an employee
#[delete("/{id}")]
pub async fn delete_employee(path: web::Path<String>) -> impl Responder {
    let employee_id = path.into_inner();
    info!("Request to delete employee with ID: {}", employee_id);
    
    match EMPLOYEE_DB.delete(&employee_id) {
        true => {
            info!("Employee with ID: {} deleted successfully", employee_id);
            HttpResponse::NoContent().finish()
        },
        false => {
            warn!("Employee with ID: {} not found", employee_id);
            HttpResponse::NotFound().json(json!({
                "message": format!("Employee with ID: {} not found", employee_id)
            }))
        }
    }
}
