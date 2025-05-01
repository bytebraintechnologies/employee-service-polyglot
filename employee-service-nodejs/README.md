# Employee Service Node.js

This is a Node.js application that provides CRUD operations for managing employee data using an in-memory database.

## Features

- Create, Read, Update, and Delete employee records
- In-memory database implementation
- RESTful API endpoints
- Comprehensive transaction logging
- Sample data initialization

## Prerequisites

- Node.js (v14.x or higher)
- npm (v6.x or higher)

## Installation

1. Navigate to the project directory:
   ```
   cd employee-service-nodejs
   ```

2. Install dependencies:
   ```
   npm install
   ```

## Running the Application

### Start the server:

```
npm start
```

### Start the server in development mode (with auto-reload):

```
npm run dev
```

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
GET http://localhost:3000/api/employees
```

### Get an employee by ID
```
GET http://localhost:3000/api/employees/1
```

### Create a new employee
```
POST http://localhost:3000/api/employees
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
PUT http://localhost:3000/api/employees/1
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
DELETE http://localhost:3000/api/employees/1
```

## Logging

The application uses Winston for logging with the following features:
- Console logging for development
- File-based logging with rotation
- Separate error log file
- HTTP request logging with Morgan

Logs are stored in the `logs` directory.
