# Employee Service - Port Assignments

This document provides the port assignments for the different implementations of the Employee Service API.

## Port Assignments

| Implementation | Port | Base URL                 | Status      |
|----------------|------|--------------------------|-------------|
| Java           | 8080 | http://localhost:8080    | Available   |
| Node.js        | 8081 | http://localhost:8081    | Available   |
| Python         | 8082 | http://localhost:8082    | Available   |
| Rust           | 8083 | http://localhost:8083    | Available   |
| C++            | 8084 | http://localhost:8084    | Available   |
| Ruby           | 8085 | http://localhost:8085    | Available   |
| C#/.NET        | 8086 | http://localhost:8086    | Available   |
| Clojure        | 8087 | http://localhost:8087    | Available   |
| Go             | 8088 | http://localhost:8088    | Available   |
| Scala          | 8089 | http://localhost:8089    | Available   |

## Running Multiple Services

Each implementation has been configured to use a different port, allowing you to run multiple versions simultaneously for comparison purposes.

## Testing with Postman

When testing with the provided Postman collections, be sure to update the port number in the request URLs to match the implementation you're testing.

Example:
- For Python: Change URLs to `http://localhost:8082/api/employees`
- For Rust: Change URLs to `http://localhost:8083/api/employees`
- For C++: Change URLs to `http://localhost:8084/api/employees`
