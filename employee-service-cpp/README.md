# Employee Service API - C++ Version

A simple RESTful API for managing employee records, implemented in C++ using the Crow framework.

## Features

- Create, Read, Update, Delete (CRUD) operations for employee records
- In-memory database with sample data preloaded
- RESTful API endpoints
- Request/Response logging
- Error handling
- Data validation

## Requirements

- C++17 compatible compiler (MSVC, GCC, Clang)
- CMake 3.14 or higher
- vcpkg package manager
- Dependencies:
  - nlohmann-json (Modern C++ JSON library)
  - Crow (C++ web framework)

## Project Structure

```
employee-service-cpp/
├── include/               # Header files
├── src/                   # Source files
│   ├── controllers/       # Request handlers
│   ├── models/            # Data models and business logic
│   ├── routes/            # Route definitions
│   ├── utils/             # Utility functions (logging, etc.)
│   └── main.cpp           # Application entry point
├── logs/                  # Log files (created at runtime)
├── build/                 # Build directory (created at build time)
├── CMakeLists.txt         # CMake build configuration
├── vcpkg.json             # vcpkg dependencies
└── run.bat                # Batch script to build and run the application
```

## Getting Started

### Prerequisites

1. Install [vcpkg](https://github.com/microsoft/vcpkg)
2. Set the `VCPKG_ROOT` environment variable to your vcpkg installation directory
3. Install [CMake](https://cmake.org/download/)

### Building and Running

#### Windows

Run the provided batch script:

```
run.bat
```

#### Manual Build

```bash
# Create build directory
mkdir build
cd build

# Configure with CMake
cmake .. -DCMAKE_TOOLCHAIN_FILE="%VCPKG_ROOT%/scripts/buildsystems/vcpkg.cmake"

# Build
cmake --build . --config Release

# Set the port and run
set PORT=8084
Release/employee_service_cpp.exe
```

By default, the server runs on port 8084. You can change this by setting the `PORT` environment variable.

## API Endpoints

### Base URL

`http://localhost:8084`

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
  "firstName": "string",    // Required
  "lastName": "string",     // Required
  "email": "string",        // Required, must be valid email
  "department": "string",   // Optional
  "salary": 0.0             // Optional, defaults to 0.0
}
```

## Logging

Logs are written to:
- `logs/app.log` - All application logs
- `logs/error.log` - Error logs only
- Console output

## License

MIT