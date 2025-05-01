# Employee Service API - Rust Version

A simple RESTful API for managing employee records, implemented in Rust using the Actix Web framework.

## Features

- Create, Read, Update, Delete (CRUD) operations for employee records
- In-memory database with sample data preloaded
- RESTful API endpoints
- Request/Response logging
- Error handling
- Data validation

## Requirements

- Rust (latest stable version)
- Cargo (included with Rust)

## Project Structure

```
employee-service-rust/
├── src/
│   ├── controllers/     # Request handlers
│   ├── models/          # Data models and business logic
│   ├── routes/          # Route definitions
│   ├── utils/           # Utility functions (logging, etc.)
│   └── main.rs          # Application entry point
├── logs/                # Log files (created at runtime)
├── Cargo.toml           # Project dependencies
├── Cargo.lock           # Lock file for dependencies
└── run.bat              # Batch script to run the application
```

## Getting Started

### Installation

1. Install Rust from https://rustup.rs/
2. Clone this repository
3. Navigate to the project directory

### Running the Application

#### Windows

Run the provided batch script:

```
run.bat
```

#### Manual Execution

```
set PORT=8083
cargo run
```

By default, the server runs on port 8083. You can change this by setting the `PORT` environment variable.

## API Endpoints

### Base URL

`http://localhost:8083`

### Endpoints

| Method | Endpoint             | Description                  |
|--------|----------------------|------------------------------|
| GET    | /                    | Home route                   |
| GET    | /api/employees       | Get all employees            |
| GET    | /api/employees/{id}  | Get employee by ID           |
| POST   | /api/employees       | Create a new employee        |
| PUT    | /api/employees/{id}  | Update an existing employee  |
| DELETE | /api/employees/{id}  | Delete an employee           |

### Employee Schema

```json
{
  "id": "string",           // Optional, auto-generated if not provided
  "first_name": "string",   // Required
  "last_name": "string",    // Required
  "email": "string",        // Required, must be valid email
  "department": "string",   // Optional
  "salary": 0.0             // Optional, defaults to 0.0
}
```

## Logging

Logs are written to:
- `logs/app.log` - All application logs
- `logs/error.log` - Error logs only
- Console output - INFO level and above

## License

MIT