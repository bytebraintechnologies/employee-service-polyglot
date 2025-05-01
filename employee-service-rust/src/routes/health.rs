use actix_web::{get, web, HttpResponse, Responder};
use serde::Serialize;
use std::time::SystemTime;
use tracing::info;
use std::process;
use sys_info;

#[derive(Serialize)]
struct SystemInfo {
    os_type: String,
    os_release: String,
    cpu_num: u32,
    cpu_speed: u64,
}

#[derive(Serialize)]
struct ResourceInfo {
    pid: u32,
    memory_usage: u64,
    total_memory: u64,
    available_memory: u64,
}

#[derive(Serialize)]
struct HealthResponse {
    status: String,
    service: String,
    time: String,
    system: SystemInfo,
    resources: ResourceInfo,
}

#[get("/health")]
async fn health_check() -> impl Responder {
    info!("Health check requested");
    
    // Get current time formatted as ISO 8601
    let now = SystemTime::now()
        .duration_since(SystemTime::UNIX_EPOCH)
        .unwrap()
        .as_secs();
    
    // Format as ISO 8601 string (simplified)
    let time_str = chrono::DateTime::from_timestamp(now as i64, 0)
        .unwrap()
        .to_rfc3339();
    
    // System information
    let system_info = SystemInfo {
        os_type: sys_info::os_type().unwrap_or_else(|_| "Unknown".to_string()),
        os_release: sys_info::os_release().unwrap_or_else(|_| "Unknown".to_string()),
        cpu_num: sys_info::cpu_num().unwrap_or(0),
        cpu_speed: sys_info::cpu_speed().unwrap_or(0),
    };
    
    // Resource information
    let resource_info = ResourceInfo {
        pid: process::id(),
        memory_usage: sys_info::mem_info().map(|m| (m.total - m.avail) * 1024).unwrap_or(0),
        total_memory: sys_info::mem_info().map(|m| m.total * 1024).unwrap_or(0),
        available_memory: sys_info::mem_info().map(|m| m.avail * 1024).unwrap_or(0),
    };
    
    let response = HealthResponse {
        status: "UP".to_string(),
        service: "employee-service-rust".to_string(),
        time: time_str,
        system: system_info,
        resources: resource_info,
    };
    
    HttpResponse::Ok().json(response)
}

pub fn config(cfg: &mut web::ServiceConfig) {
    cfg.service(health_check);
}