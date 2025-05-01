use actix_web::{get, HttpResponse, Responder};
use serde_json::json;

#[get("/")]
pub async fn home() -> impl Responder {
    HttpResponse::Ok().json(json!({
        "message": "Welcome to Employee Service API"
    }))
}
