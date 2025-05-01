from flask import jsonify, request
from http import HTTPStatus
from app.models.employee import EmployeeModel, EmployeeSchema
from loguru import logger

class EmployeeController:
    """Controller for Employee endpoints"""
    
    @staticmethod
    def get_all_employees():
        """Get all employees"""
        try:
            logger.info("Request to get all employees")
            employees = EmployeeModel.get_all()
            logger.info(f"Retrieved {len(employees)} employees successfully")
            return jsonify(employees), HTTPStatus.OK
        except Exception as e:
            logger.error(f"Error retrieving all employees: {str(e)}")
            return jsonify({"message": "Error retrieving employees", "error": str(e)}), HTTPStatus.INTERNAL_SERVER_ERROR
    
    @staticmethod
    def get_employee_by_id(employee_id):
        """Get employee by ID"""
        try:
            logger.info(f"Request to get employee with ID: {employee_id}")
            employee = EmployeeModel.get_by_id(employee_id)
            
            if not employee:
                logger.warning(f"Employee with ID: {employee_id} not found")
                return jsonify({"message": f"Employee with ID: {employee_id} not found"}), HTTPStatus.NOT_FOUND
            
            logger.info(f"Retrieved employee with ID: {employee_id} successfully")
            return jsonify(employee), HTTPStatus.OK
        except Exception as e:
            logger.error(f"Error retrieving employee with ID {employee_id}: {str(e)}")
            return jsonify({"message": "Error retrieving employee", "error": str(e)}), HTTPStatus.INTERNAL_SERVER_ERROR
    
    @staticmethod
    def create_employee():
        """Create a new employee"""
        try:
            logger.info("Request to create a new employee")
            
            # Get request data
            employee_data = request.json
            logger.debug(f"Request data: {employee_data}")
            
            # Validate input data
            try:
                EmployeeSchema(**employee_data)
            except Exception as e:
                logger.warning(f"Invalid employee data: {str(e)}")
                return jsonify({"message": "Invalid employee data", "error": str(e)}), HTTPStatus.BAD_REQUEST
            
            # Create employee
            new_employee = EmployeeModel.create(employee_data)
            logger.info(f"Employee created successfully with ID: {new_employee['id']}")
            
            return jsonify(new_employee), HTTPStatus.CREATED
        except Exception as e:
            logger.error(f"Error creating employee: {str(e)}")
            return jsonify({"message": "Error creating employee", "error": str(e)}), HTTPStatus.INTERNAL_SERVER_ERROR
    
    @staticmethod
    def update_employee(employee_id):
        """Update an employee"""
        try:
            logger.info(f"Request to update employee with ID: {employee_id}")
            
            # Get request data
            employee_data = request.json
            logger.debug(f"Request data: {employee_data}")
            
            # Update employee
            updated_employee = EmployeeModel.update(employee_id, employee_data)
            
            if not updated_employee:
                logger.warning(f"Employee with ID: {employee_id} not found")
                return jsonify({"message": f"Employee with ID: {employee_id} not found"}), HTTPStatus.NOT_FOUND
            
            logger.info(f"Employee with ID: {employee_id} updated successfully")
            return jsonify(updated_employee), HTTPStatus.OK
        except Exception as e:
            logger.error(f"Error updating employee with ID {employee_id}: {str(e)}")
            return jsonify({"message": "Error updating employee", "error": str(e)}), HTTPStatus.INTERNAL_SERVER_ERROR
    
    @staticmethod
    def delete_employee(employee_id):
        """Delete an employee"""
        try:
            logger.info(f"Request to delete employee with ID: {employee_id}")
            
            # Delete employee
            result = EmployeeModel.delete(employee_id)
            
            if not result:
                logger.warning(f"Employee with ID: {employee_id} not found")
                return jsonify({"message": f"Employee with ID: {employee_id} not found"}), HTTPStatus.NOT_FOUND
            
            logger.info(f"Employee with ID: {employee_id} deleted successfully")
            return "", HTTPStatus.NO_CONTENT
        except Exception as e:
            logger.error(f"Error deleting employee with ID {employee_id}: {str(e)}")
            return jsonify({"message": "Error deleting employee", "error": str(e)}), HTTPStatus.INTERNAL_SERVER_ERROR
