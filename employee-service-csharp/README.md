# Employee Service - C#/.NET Implementation

A RESTful API for employee management implemented in C# with ASP.NET Core.

## Features

- RESTful API endpoints for employee management
- In-memory database with sample data
- CRUD operations (Create, Read, Update, Delete)
- Serilog for structured logging
- Swagger/OpenAPI documentation
- Error handling and validation using DataAnnotations

## Prerequisites

- .NET 8.0 SDK or later

## Getting Started

1. Clone the repository
2. Navigate to the project directory
3. Run the application with the provided batch file:

```
run.bat
```

Or manually:

```
dotnet run
```

4. Open your browser to access Swagger UI: http://localhost:8086/swagger

## API Endpoints

| HTTP Method | Endpoint | Description |
|-------------|----------|-------------|
| GET | /api/employees | Get all employees |
| GET | /api/employees/{id} | Get employee by ID |
| POST | /api/employees | Create new employee |
| PUT | /api/employees/{id} | Update employee |
| DELETE | /api/employees/{id} | Delete employee |
| GET | /health | Health check endpoint |

## Employee Model

```json
{
  "id": "string",
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "department": "string",
  "salary": number
}
```

## Example Requests

### Get All Employees

```
GET /api/employees
```

### Get Employee by ID

```
GET /api/employees/1
```

### Create Employee

```
POST /api/employees

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "department": "IT",
  "salary": 75000.0
}
```

### Update Employee

```
PUT /api/employees/1

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.updated@example.com",
  "department": "Engineering",
  "salary": 80000.0
}
```

### Delete Employee

```
DELETE /api/employees/1
```

## Project Structure

```
employee-service-csharp/
├── Controllers/              # API controllers
│   ├── EmployeeController.cs # Employee API endpoints
│   └── HealthController.cs   # Health check endpoint
├── Models/                   # Data models
│   └── Employee.cs           # Employee model
├── Services/                 # Business logic
│   ├── IEmployeeService.cs   # Interface
│   └── EmployeeService.cs    # Implementation
├── Program.cs                # Application entry point
├── employee-service-csharp.csproj # Project file
└── Properties/               # Application settings
    └── launchSettings.json   # Launch configuration
```
