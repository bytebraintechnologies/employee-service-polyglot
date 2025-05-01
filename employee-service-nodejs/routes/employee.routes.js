const express = require('express');
const router = express.Router();
const employeeController = require('../controllers/employee.controller');

// Get all employees
router.get('/', employeeController.getAllEmployees);

// Get employee by ID
router.get('/:id', employeeController.getEmployeeById);

// Create a new employee
router.post('/', employeeController.createEmployee);

// Update an employee
router.put('/:id', employeeController.updateEmployee);

// Delete an employee
router.delete('/:id', employeeController.deleteEmployee);

module.exports = router;
