# Employee Service - Ruby Implementation

A RESTful API for employee management implemented in Ruby with Sinatra.

## Features

- RESTful API endpoints for employee management
- In-memory database with sample data
- CRUD operations (Create, Read, Update, Delete)
- Logging with Ruby's Logger
- Error handling and validation

## Prerequisites

- Ruby 3.0 or higher
- Bundler

## Getting Started

1. Clone the repository
2. Navigate to the project directory
3. Run the application with the provided batch file:

```
run.bat
```

Or manually:

```
bundle install
bundle exec ruby app.rb
```

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
employee-service-ruby/
├── app.rb                   # Main application file
├── Gemfile                  # Dependencies
├── run.bat                  # Run script
├── config/
│   └── logger.rb            # Logging configuration
├── controllers/
│   └── employee_controller.rb # Employee controller
├── models/
│   └── employee.rb          # Employee model and repository
└── logs/                    # Log files
```
