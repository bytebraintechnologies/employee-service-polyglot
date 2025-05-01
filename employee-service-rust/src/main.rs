use actix_cors::Cors;
use actix_web::{middleware, App, HttpServer};
use dotenv::dotenv;
use std::env;
use tracing::info;

mod controllers;
mod models;
mod routes;
mod utils;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    // Load environment variables from .env file if it exists
    dotenv().ok();

    // Initialize logging
    utils::logging::init_logging();

    // Get the port from environment variables or use default
    let port = env::var("PORT").unwrap_or_else(|_| "8080".to_string());
    let address = format!("0.0.0.0:{}", port);

    info!("Starting Employee Service Rust Application on {}", address);

    // Start HTTP server
    HttpServer::new(|| {
        // Configure CORS
        let cors = Cors::default()
            .allow_any_origin()
            .allow_any_method()
            .allow_any_header()
            .max_age(3600);

        App::new()
            // Enable logger middleware
            .wrap(middleware::Logger::default())
            // Enable CORS
            .wrap(cors)
            // Register routes
            .configure(routes::employee_routes::config)
            // Root route
            .service(routes::home::home)
    })
    .bind(address)?
    .run()
    .await
}
