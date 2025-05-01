# Employee Service Go

This is a Go application that provides CRUD operations for managing employee data using an in-memory database.

## Features

- Create, Read, Update, and Delete employee records
- In-memory database implementation with thread-safety
- RESTful API endpoints using Gin web framework
- Comprehensive transaction logging with Logrus
- Sample data initialization
- Request middleware for logging

## Prerequisites

- Go (v1.16 or higher)

## Installation

1. Navigate to the project directory:
   ```
   cd employee-service-go
   ```

2. Download dependencies:
   ```
   go mod download
   ```

## Running the Application

### Option 1: Using the provided batch file (Windows)

1. Double-click on the `run.bat` file in the project root directory

### Option 2: Using the command line

1. Build the application:
   ```
   go build -o employee-service-go
   ```

2. Run the application:
   ```
   ./employee-service-go
   ```

The application will start on http://localhost:7070

## API Endpoints

| HTTP Method | Endpoint | Description |
|-------------|----------|-------------|
| GET | /api/employees | Get all employees |
| GET | /api/employees/:id | Get employee by ID |
| POST | /api/employees | Create new employee |
| PUT | /api/employees/:id | Update employee |
| DELETE | /api/employees/:id | Delete employee |

## API Usage Examples

### Get all employees
```
GET http://localhost:7070/api/employees
```

### Get an employee by ID
```
GET http://localhost:7070/api/employees/1
```

### Create a new employee
```
POST http://localhost:7070/api/employees
Content-Type: application/json

{
  "firstName": "Robert",
  "lastName": "Johnson",
  "email": "robert.johnson@example.com",
  "department": "Engineering",
  "salary": 78000.0
}
```

### Update an employee
```
PUT http://localhost:7070/api/employees/1
Content-Type: application/json

{
  "firstName": "Robert",
  "lastName": "Johnson",
  "email": "robert.johnson.updated@example.com",
  "department": "Research",
  "salary": 82000.0
}
```

### Delete an employee
```
DELETE http://localhost:7070/api/employees/1
```

## Logging

The application uses Logrus for logging with the following features:
- Console and file logging simultaneously
- Daily log rotation
- Request/response logging with timing information
- Detailed transaction logging

Logs are stored in the `logs` directory.

## Project Structure

```
employee-service-go/
├── controllers/
│   └── employee_controller.go
├── models/
│   └── employee.go
├── routes/
│   └── employee_routes.go
├── utils/
│   └── logger.go
├── logs/
├── go.mod
├── go.sum
├── main.go
├── README.md
├── run.bat
└── .gitignore
```
