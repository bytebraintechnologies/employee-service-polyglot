const Employee = require('../models/employee.model');
const logger = require('../config/logger');

/**
 * Employee controller with CRUD operations
 */
const employeeController = {
  /**
   * Get all employees
   * @param {object} req - Express request object
   * @param {object} res - Express response object
   */
  getAllEmployees: (req, res) => {
    try {
      logger.info('Request to get all employees');
      const employees = Employee.findAll();
      logger.info(`Retrieved ${employees.length} employees successfully`);
      res.status(200).json(employees);
    } catch (error) {
      logger.error(`Error retrieving employees: ${error.message}`);
      res.status(500).json({ message: 'Error retrieving employees', error: error.message });
    }
  },

  /**
   * Get employee by ID
   * @param {object} req - Express request object
   * @param {object} res - Express response object
   */
  getEmployeeById: (req, res) => {
    try {
      const id = req.params.id;
      logger.info(`Request to get employee with ID: ${id}`);
      
      const employee = Employee.findById(id);
      
      if (!employee) {
        logger.warn(`Employee with ID: ${id} not found`);
        return res.status(404).json({ message: `Employee with ID: ${id} not found` });
      }
      
      logger.info(`Retrieved employee with ID: ${id} successfully`);
      res.status(200).json(employee);
    } catch (error) {
      logger.error(`Error retrieving employee: ${error.message}`);
      res.status(500).json({ message: 'Error retrieving employee', error: error.message });
    }
  },

  /**
   * Create a new employee
   * @param {object} req - Express request object
   * @param {object} res - Express response object
   */
  createEmployee: (req, res) => {
    try {
      logger.info('Request to create new employee');
      
      // Basic validation
      const { firstName, lastName, email, department, salary } = req.body;
      
      if (!firstName || !lastName || !email) {
        logger.warn('Failed to create employee: Missing required fields');
        return res.status(400).json({ message: 'First name, last name, and email are required fields' });
      }
      
      // Create employee
      const newEmployee = Employee.create(req.body);
      
      logger.info(`Employee created successfully with ID: ${newEmployee.id}`);
      res.status(201).json(newEmployee);
    } catch (error) {
      logger.error(`Error creating employee: ${error.message}`);
      res.status(500).json({ message: 'Error creating employee', error: error.message });
    }
  },

  /**
   * Update an employee
   * @param {object} req - Express request object
   * @param {object} res - Express response object
   */
  updateEmployee: (req, res) => {
    try {
      const id = req.params.id;
      logger.info(`Request to update employee with ID: ${id}`);
      
      const updatedEmployee = Employee.update(id, req.body);
      
      if (!updatedEmployee) {
        logger.warn(`Employee with ID: ${id} not found for update`);
        return res.status(404).json({ message: `Employee with ID: ${id} not found` });
      }
      
      logger.info(`Employee with ID: ${id} updated successfully`);
      res.status(200).json(updatedEmployee);
    } catch (error) {
      logger.error(`Error updating employee: ${error.message}`);
      res.status(500).json({ message: 'Error updating employee', error: error.message });
    }
  },

  /**
   * Delete an employee
   * @param {object} req - Express request object
   * @param {object} res - Express response object
   */
  deleteEmployee: (req, res) => {
    try {
      const id = req.params.id;
      logger.info(`Request to delete employee with ID: ${id}`);
      
      const deleted = Employee.delete(id);
      
      if (!deleted) {
        logger.warn(`Employee with ID: ${id} not found for deletion`);
        return res.status(404).json({ message: `Employee with ID: ${id} not found` });
      }
      
      logger.info(`Employee with ID: ${id} deleted successfully`);
      res.status(204).send();
    } catch (error) {
      logger.error(`Error deleting employee: ${error.message}`);
      res.status(500).json({ message: 'Error deleting employee', error: error.message });
    }
  }
};

module.exports = employeeController;
