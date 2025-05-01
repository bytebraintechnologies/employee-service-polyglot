# Employee Service

This is a Spring Boot application that provides CRUD operations for managing employee data using an in-memory H2 database.

## Features

- Create, Read, Update, and Delete employee records
- In-memory H2 database
- RESTful API endpoints
- Transaction logging
- Sample data initialization

## Prerequisites

- Java 17 or higher
- Maven (or use the included Maven wrapper)

## Running the Application

### Option 1: Using the provided batch file

1. Double-click on the `run.bat` file in the project root directory

### Option 2: Using the command line

1. Open a command prompt in the project root directory
2. Run the following command:
   ```
   mvnw.cmd spring-boot:run
   ```

## Accessing the Application

- The application runs on http://localhost:8080
- H2 Database Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:employeedb`
  - Username: `sa`
  - Password: (leave blank)

## API Endpoints

| HTTP Method | Endpoint | Description |
|-------------|----------|-------------|
| GET | /api/employees | Get all employees |
| GET | /api/employees/{id} | Get employee by ID |
| POST | /api/employees | Create new employee |
| PUT | /api/employees/{id} | Update employee |
| DELETE | /api/employees/{id} | Delete employee |

## Postman Collection

Import the provided `Employee_Service_Postman_Collection.json` file into Postman to test all API endpoints.

## Logging

Logging is configured to show transaction details. Check the console output to see logging information.
