const { v4: uuidv4 } = require('uuid');
const logger = require('../config/logger');

// In-memory database for employees
let employees = [
  {
    id: '1',
    firstName: 'John',
    lastName: 'Doe',
    email: 'john.doe@example.com',
    department: 'IT',
    salary: 75000.0
  },
  {
    id: '2',
    firstName: 'Jane',
    lastName: 'Smith',
    email: 'jane.smith@example.com',
    department: 'HR',
    salary: 65000.0
  },
  {
    id: '3',
    firstName: 'Michael',
    lastName: 'Brown',
    email: 'michael.brown@example.com',
    department: 'Finance',
    salary: 85000.0
  },
  {
    id: '4',
    firstName: 'Sarah',
    lastName: 'Johnson',
    email: 'sarah.johnson@example.com',
    department: 'Marketing',
    salary: 70000.0
  },
  {
    id: '5',
    firstName: 'David',
    lastName: 'Williams',
    email: 'david.williams@example.com',
    department: 'Operations',
    salary: 72000.0
  }
];

logger.info('Initialized in-memory employee database with sample data');

class EmployeeModel {
  /**
   * Get all employees
   * @returns {Array} List of all employees
   */
  findAll() {
    logger.debug('Retrieving all employees from in-memory database');
    return [...employees];
  }

  /**
   * Find employee by ID
   * @param {string} id - Employee ID
   * @returns {Object|null} Employee object or null if not found
   */
  findById(id) {
    logger.debug(`Finding employee with ID: ${id}`);
    return employees.find(employee => employee.id === id) || null;
  }

  /**
   * Create a new employee
   * @param {Object} employeeData - New employee data
   * @returns {Object} Created employee
   */
  create(employeeData) {
    const newEmployee = {
      id: uuidv4(),
      ...employeeData
    };
    
    employees.push(newEmployee);
    logger.info(`Created new employee with ID: ${newEmployee.id}`);
    
    return newEmployee;
  }

  /**
   * Update an existing employee
   * @param {string} id - Employee ID
   * @param {Object} employeeData - Updated employee data
   * @returns {Object|null} Updated employee or null if not found
   */
  update(id, employeeData) {
    logger.debug(`Attempting to update employee with ID: ${id}`);
    
    const index = employees.findIndex(employee => employee.id === id);
    
    if (index === -1) {
      logger.warn(`Employee with ID: ${id} not found`);
      return null;
    }
    
    // Preserve the ID and update other fields
    const updatedEmployee = {
      ...employees[index],
      ...employeeData,
      id // Ensure ID doesn't change
    };
    
    employees[index] = updatedEmployee;
    logger.info(`Successfully updated employee with ID: ${id}`);
    
    return updatedEmployee;
  }

  /**
   * Delete an employee
   * @param {string} id - Employee ID
   * @returns {boolean} True if deleted, false if not found
   */
  delete(id) {
    logger.debug(`Attempting to delete employee with ID: ${id}`);
    
    const initialLength = employees.length;
    employees = employees.filter(employee => employee.id !== id);
    
    const deleted = employees.length < initialLength;
    
    if (deleted) {
      logger.info(`Successfully deleted employee with ID: ${id}`);
    } else {
      logger.warn(`Employee with ID: ${id} not found for deletion`);
    }
    
    return deleted;
  }
}

module.exports = new EmployeeModel();
