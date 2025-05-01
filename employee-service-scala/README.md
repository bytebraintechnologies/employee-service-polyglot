# Employee Service - Scala Implementation

This is a Scala implementation of the Employee Service API using Akka HTTP. It provides a RESTful API for employee management with full CRUD operations.

## Technologies Used

- Scala 2.13
- Akka HTTP
- Circe for JSON handling
- SBT for build management
- Logback for logging

## Prerequisites

- Java 8 or higher
- SBT (Scala Build Tool)

## Getting Started

1. Clone the repository
2. Navigate to the `employee-service-scala` directory
3. Run the application using the provided batch file:
   ```
   run.bat
   ```
   
   Or directly with SBT:
   ```
   sbt run
   ```

4. The service will start on port 8085 (configurable in application.conf)

## API Endpoints

### Get all employees
```
GET http://localhost:8085/api/employees
```

### Get a specific employee
```
GET http://localhost:8085/api/employees/{id}
```

### Create a new employee
```
POST http://localhost:8085/api/employees
```

Request body example:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "555-123-4567",
  "position": "Software Engineer",
  "department": "Engineering",
  "salary": 85000.00,
  "hireDate": "2023-01-15"
}
```

### Update an employee
```
PUT http://localhost:8085/api/employees/{id}
```
(Same request body format as POST)

### Delete an employee
```
DELETE http://localhost:8085/api/employees/{id}
```

## Configuration

The application's configuration is in `src/main/resources/application.conf`. Key settings include:

- HTTP server port (default: 8085)
- Logging levels
- Application name

## Project Structure

```
employee-service-scala/
│
├── src/main/scala/com/example/employeeservice/
│   ├── controller/          # HTTP API endpoints
│   ├── model/               # Data models
│   ├── service/             # Business logic
│   ├── repository/          # Data access layer
│   ├── config/              # Configuration classes
│   └── EmployeeServiceApp.scala # Main application entry point
│
├── src/main/resources/
│   ├── application.conf     # Application configuration
│   └── logback.xml          # Logging configuration
│
├── build.sbt                # SBT build definition
└── run.bat                  # Windows batch file to run the application
```

## In-Memory Database

This implementation uses an in-memory database (concurrent TrieMap) for storing employee data. The data is lost when the application restarts.
