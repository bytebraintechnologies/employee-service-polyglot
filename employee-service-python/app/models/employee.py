import uuid
from typing import Optional, Dict, List, Any
from pydantic import BaseModel, Field, EmailStr
from loguru import logger

class EmployeeSchema(BaseModel):
    """Schema for Employee data validation"""
    id: Optional[str] = None
    firstName: str
    lastName: str
    email: str
    department: Optional[str] = None
    salary: Optional[float] = 0.0

    class Config:
        from_attributes = True

# In-memory database for employees
class EmployeeDatabase:
    """In-memory database for Employee records"""
    
    def __init__(self):
        self.employees: Dict[str, Dict[str, Any]] = {}
        self._initialize_sample_data()
    
    def _initialize_sample_data(self):
        """Initialize the database with sample employee data"""
        sample_employees = [
            {
                "id": "1",
                "firstName": "John",
                "lastName": "Doe",
                "email": "john.doe@example.com",
                "department": "IT",
                "salary": 75000.0
            },
            {
                "id": "2",
                "firstName": "Jane",
                "lastName": "Smith",
                "email": "jane.smith@example.com",
                "department": "HR",
                "salary": 65000.0
            },
            {
                "id": "3",
                "firstName": "Michael",
                "lastName": "Brown",
                "email": "michael.brown@example.com",
                "department": "Finance",
                "salary": 85000.0
            },
            {
                "id": "4",
                "firstName": "Sarah",
                "lastName": "Johnson",
                "email": "sarah.johnson@example.com",
                "department": "Marketing",
                "salary": 70000.0
            },
            {
                "id": "5",
                "firstName": "David",
                "lastName": "Williams",
                "email": "david.williams@example.com",
                "department": "Operations",
                "salary": 72000.0
            }
        ]
        
        for employee in sample_employees:
            self.employees[employee["id"]] = employee
        
        logger.info(f"Initialized in-memory employee database with {len(self.employees)} sample records")

# Singleton instance of the employee database
employee_db = EmployeeDatabase()

class EmployeeModel:
    """Model for Employee CRUD operations"""
    
    @staticmethod
    def get_all() -> List[Dict[str, Any]]:
        """Get all employees"""
        logger.info("Retrieving all employees")
        return list(employee_db.employees.values())
    
    @staticmethod
    def get_by_id(employee_id: str) -> Optional[Dict[str, Any]]:
        """Get employee by ID"""
        logger.info(f"Retrieving employee with ID: {employee_id}")
        employee = employee_db.employees.get(employee_id)
        
        if not employee:
            logger.warning(f"Employee with ID: {employee_id} not found")
            
        return employee
    
    @staticmethod
    def create(employee_data: Dict[str, Any]) -> Dict[str, Any]:
        """Create a new employee"""
        logger.info("Creating new employee")
        
        # Generate new ID if not provided
        if not employee_data.get("id"):
            employee_data["id"] = str(uuid.uuid4())
        
        # Add to database
        employee_db.employees[employee_data["id"]] = employee_data
        
        logger.info(f"Created employee with ID: {employee_data['id']}")
        return employee_data
    
    @staticmethod
    def update(employee_id: str, employee_data: Dict[str, Any]) -> Optional[Dict[str, Any]]:
        """Update an existing employee"""
        logger.info(f"Updating employee with ID: {employee_id}")
        
        if employee_id not in employee_db.employees:
            logger.warning(f"Employee with ID: {employee_id} not found")
            return None
        
        # Preserve ID and update other fields
        current_employee = employee_db.employees[employee_id]
        for key, value in employee_data.items():
            if key != "id":  # Don't allow ID to be changed
                current_employee[key] = value
        
        logger.info(f"Updated employee with ID: {employee_id}")
        return current_employee
    
    @staticmethod
    def delete(employee_id: str) -> bool:
        """Delete an employee"""
        logger.info(f"Deleting employee with ID: {employee_id}")
        
        if employee_id not in employee_db.employees:
            logger.warning(f"Employee with ID: {employee_id} not found")
            return False
        
        del employee_db.employees[employee_id]
        logger.info(f"Deleted employee with ID: {employee_id}")
        return True
