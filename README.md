# Employee Service Multi-Implementation

This project provides implementations of an Employee CRUD Service with an in-memory database in four different programming languages/frameworks:

1. Java (Spring Boot)
2. Node.js (Express)
3. Python (Flask)
4. Go (Gin)

Each implementation follows similar architecture patterns while leveraging the idiomatic approaches of its respective language.

## Common Features Across All Implementations

- RESTful API endpoints for employee management
- In-memory database with sample data initialization
- CRUD operations (Create, Read, Update, Delete)
- Comprehensive transaction logging
- Error handling and validation
- Postman collection for testing

## API Endpoints (Same Across All Implementations)

| HTTP Method | Endpoint | Description |
|-------------|----------|-------------|
| GET | /api/employees | Get all employees |
| GET | /api/employees/{id} | Get employee by ID |
| POST | /api/employees | Create new employee |
| PUT | /api/employees/{id} | Update employee |
| DELETE | /api/employees/{id} | Delete employee |

## Implementation Specifics

### 1. Java (Spring Boot)

**Directory**: `employee-service`

**Features**:
- Spring Boot with Spring Data JPA
- H2 in-memory database
- Annotation-based validation
- Transaction management
- Structured logging

**Running the Application**:
- Navigate to the directory: `cd employee-service`
- Run the batch file: `run.bat`
- Access at: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console

**Strengths**:
- Strong type safety
- Robust ORM capabilities
- Extensive ecosystem
- Enterprise-grade features

### 2. Node.js (Express)

**Directory**: `employee-service-nodejs`

**Features**:
- Express web framework
- Array-based in-memory storage
- Winston and Morgan for logging
- CORS support
- Middleware-based architecture

**Running the Application**:
- Navigate to the directory: `cd employee-service-nodejs`
- Run the batch file: `run.bat`
- Access at: http://localhost:3000

**Strengths**:
- JavaScript simplicity
- Non-blocking I/O
- Lightweight and fast
- Excellent for real-time applications

### 3. Python (Flask)

**Directory**: `employee-service-python`

**Features**:
- Flask web framework
- Dictionary-based in-memory storage
- Loguru for structured logging
- Pydantic for data validation
- Blueprint-based routing

**Running the Application**:
- Navigate to the directory: `cd employee-service-python`
- Run the batch file: `run.bat`
- Access at: http://localhost:5000

**Strengths**:
- Easy to read and write
- Rapid development
- Strong data validation
- Excellent for data processing

### 4. Go (Gin)

**Directory**: `employee-service-go`

**Features**:
- Gin web framework
- Map-based in-memory storage with mutex for concurrency
- Logrus for structured logging
- Built-in validation
- Thread-safe operations

**Running the Application**:
- Navigate to the directory: `cd employee-service-go`
- Run the batch file: `run.bat`
- Access at: http://localhost:8080

**Strengths**:
- High performance
- Built-in concurrency
- Strong static typing
- Memory efficiency
- Excellent for microservices

## Which Implementation to Choose?

The choice between these implementations depends on your specific needs:

- **Java (Spring Boot)**: Best for enterprise applications where robustness, type safety, and extensive ecosystem are important
- **Node.js**: Great for real-time applications, microservices, and when you need JavaScript across the stack
- **Python**: Excellent for rapid development, data processing, and when readability is a priority
- **Go**: Ideal for high-performance services, microservices where concurrency matters, and when memory efficiency is important

Each implementation demonstrates the idiomatic patterns and best practices of its respective language and framework.

## Testing

Each implementation includes its own Postman collection for testing the API endpoints:

- Java: `Employee_Service_Postman_Collection.json`
- Node.js: `Employee_Service_NodeJS_Postman_Collection.json`
- Python: `Employee_Service_Python_Postman_Collection.json`
- Go: `Employee_Service_Go_Postman_Collection.json`

Import the appropriate collection into Postman to test the corresponding implementation.

## Project Structure

```
root/
├── employee-service/             # Java (Spring Boot) implementation
├── employee-service-nodejs/      # Node.js (Express) implementation
├── employee-service-python/      # Python (Flask) implementation
├── employee-service-go/          # Go (Gin) implementation
└── README.md                     # This file
```

## Getting Started

1. Choose the implementation that best suits your needs
2. Navigate to the corresponding directory
3. Run the `run.bat` file in that directory
4. Use the provided Postman collection to test the API
