from flask import Blueprint, request
from app.controllers.employee_controller import EmployeeController
from loguru import logger
import time

# Create a Blueprint for employee routes
employee_bp = Blueprint('employee', __name__)

# Middleware for logging request details
@employee_bp.before_request
def log_request_info():
    """Log request details before processing"""
    logger.info(f"Request: {request.method} {request.path}")
    # Store request start time for duration calculation
    request.start_time = time.time()

# Middleware for logging response details
@employee_bp.after_request
def log_response_info(response):
    """Log response details after processing"""
    duration = time.time() - request.start_time
    logger.info(f"Response: {response.status_code} - took {duration:.4f}s")
    return response

# Route: Get all employees
@employee_bp.route('', methods=['GET'])
def get_all_employees():
    """Get all employees endpoint"""
    return EmployeeController.get_all_employees()

# Route: Get employee by ID
@employee_bp.route('/<employee_id>', methods=['GET'])
def get_employee_by_id(employee_id):
    """Get employee by ID endpoint"""
    return EmployeeController.get_employee_by_id(employee_id)

# Route: Create a new employee
@employee_bp.route('', methods=['POST'])
def create_employee():
    """Create a new employee endpoint"""
    return EmployeeController.create_employee()

# Route: Update an employee
@employee_bp.route('/<employee_id>', methods=['PUT'])
def update_employee(employee_id):
    """Update an employee endpoint"""
    return EmployeeController.update_employee(employee_id)

# Route: Delete an employee
@employee_bp.route('/<employee_id>', methods=['DELETE'])
def delete_employee(employee_id):
    """Delete an employee endpoint"""
    return EmployeeController.delete_employee(employee_id)
