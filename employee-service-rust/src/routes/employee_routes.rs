use actix_web::web;
use std::time::Instant;
use tracing::info;

use crate::controllers::employee_controller;

// Configure employee routes
pub fn config(cfg: &mut web::ServiceConfig) {
    cfg.service(
        web::scope("/api/employees")
            // Add middleware for request logging
            .wrap(RequestTimer)
            // Register employee routes
            .service(employee_controller::get_all_employees)
            .service(employee_controller::get_employee_by_id)
            .service(employee_controller::create_employee)
            .service(employee_controller::update_employee)
            .service(employee_controller::delete_employee),
    );
}

// Request timer middleware
use actix_web::{
    dev::{self, Service, ServiceRequest, ServiceResponse, Transform},
    Error,
};
use futures::future::{ok, Ready};
use futures::Future;
use std::pin::Pin;
use std::task::{Context, Poll};

// Middleware for logging request timing
pub struct RequestTimer;

impl<S, B> Transform<S, ServiceRequest> for RequestTimer
where
    S: Service<ServiceRequest, Response = ServiceResponse<B>, Error = Error>,
    S::Future: 'static,
    B: 'static,
{
    type Response = ServiceResponse<B>;
    type Error = Error;
    type InitError = ();
    type Transform = RequestTimerMiddleware<S>;
    type Future = Ready<Result<Self::Transform, Self::InitError>>;

    fn new_transform(&self, service: S) -> Self::Future {
        ok(RequestTimerMiddleware { service })
    }
}

pub struct RequestTimerMiddleware<S> {
    service: S,
}

impl<S, B> Service<ServiceRequest> for RequestTimerMiddleware<S>
where
    S: Service<ServiceRequest, Response = ServiceResponse<B>, Error = Error>,
    S::Future: 'static,
    B: 'static,
{
    type Response = ServiceResponse<B>;
    type Error = Error;
    type Future = Pin<Box<dyn Future<Output = Result<Self::Response, Self::Error>>>>;

    fn poll_ready(&self, cx: &mut Context<'_>) -> Poll<Result<(), Self::Error>> {
        self.service.poll_ready(cx)
    }

    fn call(&self, req: ServiceRequest) -> Self::Future {
        // Log request details
        info!("Request: {} {}", req.method(), req.path());
        let start = Instant::now();

        let fut = self.service.call(req);

        Box::pin(async move {
            // Process request
            let res = fut.await?;
            
            // Log response details
            let duration = start.elapsed();
            info!(
                "Response: {} - took {:.4}s",
                res.status().as_u16(),
                duration.as_secs_f32()
            );
            
            Ok(res)
        })
    }
}
