# Employee Service Python

This is a Python Flask application that provides CRUD operations for managing employee data using an in-memory database.

## Features

- Create, Read, Update, and Delete employee records
- In-memory database implementation
- RESTful API endpoints
- Comprehensive transaction logging with Loguru
- Sample data initialization
- Pydantic data validation

## Prerequisites

- Python 3.8 or higher
- Pip (Python package manager)

## Installation

1. Navigate to the project directory:
   ```
   cd employee-service-python
   ```

2. Create a virtual environment (optional but recommended):
   ```
   python -m venv venv
   ```

3. Activate the virtual environment:
   - On Windows:
     ```
     venv\Scripts\activate
     ```
   - On macOS/Linux:
     ```
     source venv/bin/activate
     ```

4. Install dependencies:
   ```
   pip install -r requirements.txt
   ```

## Running the Application

### Option 1: Using the provided batch file (Windows)

1. Double-click on the `run.bat` file in the project root directory

### Option 2: Using the command line

1. Activate the virtual environment (if you created one)
2. Run the following command:
   ```
   python run.py
   ```

The application will start on http://localhost:5000

## API Endpoints

| HTTP Method | Endpoint | Description |
|-------------|----------|-------------|
| GET | /api/employees | Get all employees |
| GET | /api/employees/{id} | Get employee by ID |
| POST | /api/employees | Create new employee |
| PUT | /api/employees/{id} | Update employee |
| DELETE | /api/employees/{id} | Delete employee |

## API Usage Examples

### Get all employees
```
GET http://localhost:5000/api/employees
```

### Get an employee by ID
```
GET http://localhost:5000/api/employees/1
```

### Create a new employee
```
POST http://localhost:5000/api/employees
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
PUT http://localhost:5000/api/employees/1
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
DELETE http://localhost:5000/api/employees/1
```

## Logging

The application uses Loguru for logging with the following features:
- Colorized console logging
- File-based logging with rotation
- Separate error log file
- Request/response logging with timing information

Logs are stored in the `logs` directory.

## Project Structure

```
employee-service-python/
├── app/
│   ├── controllers/
│   │   └── employee_controller.py
│   ├── models/
│   │   └── employee.py
│   ├── routes/
│   │   └── employee_routes.py
│   ├── utils/
│   │   └── logging_config.py
│   └── __init__.py
├── logs/
├── README.md
├── requirements.txt
├── run.bat
└── run.py
```
