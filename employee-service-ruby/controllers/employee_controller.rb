require_relative '../models/employee'
require_relative '../config/logger'
require 'multi_json'

# Employee controller with CRUD operations
class EmployeeController
  def initialize
    @employee_repository = EmployeeRepository.new
  end

  # Get all employees
  def get_all_employees
    begin
      Logger.info('Request to get all employees')
      employees = @employee_repository.find_all
      Logger.info("Retrieved #{employees.length} employees successfully")
      [200, { 'Content-Type' => 'application/json' }, MultiJson.dump(employees)]
    rescue StandardError => e
      Logger.error("Error retrieving employees: #{e.message}")
      [500, { 'Content-Type' => 'application/json' }, MultiJson.dump({ message: 'Error retrieving employees', error: e.message })]
    end
  end

  # Get employee by ID
  def get_employee_by_id(id)
    begin
      Logger.info("Request to get employee with ID: #{id}")
      
      employee = @employee_repository.find_by_id(id)
      
      if employee.nil?
        Logger.warn("Employee with ID: #{id} not found")
        return [404, { 'Content-Type' => 'application/json' }, MultiJson.dump({ message: "Employee with ID: #{id} not found" })]
      end
      
      Logger.info("Retrieved employee with ID: #{id} successfully")
      [200, { 'Content-Type' => 'application/json' }, MultiJson.dump(employee)]
    rescue StandardError => e
      Logger.error("Error retrieving employee: #{e.message}")
      [500, { 'Content-Type' => 'application/json' }, MultiJson.dump({ message: 'Error retrieving employee', error: e.message })]
    end
  end

  # Create a new employee
  def create_employee(params)
    begin
      Logger.info('Request to create new employee')
      
      # Basic validation
      if params['firstName'].nil? || params['lastName'].nil? || params['email'].nil?
        Logger.warn('Failed to create employee: Missing required fields')
        return [400, { 'Content-Type' => 'application/json' }, MultiJson.dump({ message: 'First name, last name, and email are required fields' })]
      end
      
      # Create employee
      new_employee = @employee_repository.create(params)
      
      Logger.info("Employee created successfully with ID: #{new_employee[:id]}")
      [201, { 'Content-Type' => 'application/json' }, MultiJson.dump(new_employee)]
    rescue StandardError => e
      Logger.error("Error creating employee: #{e.message}")
      [500, { 'Content-Type' => 'application/json' }, MultiJson.dump({ message: 'Error creating employee', error: e.message })]
    end
  end

  # Update an employee
  def update_employee(id, params)
    begin
      Logger.info("Request to update employee with ID: #{id}")
      
      updated_employee = @employee_repository.update(id, params)
      
      if updated_employee.nil?
        Logger.warn("Employee with ID: #{id} not found for update")
        return [404, { 'Content-Type' => 'application/json' }, MultiJson.dump({ message: "Employee with ID: #{id} not found" })]
      end
      
      Logger.info("Employee with ID: #{id} updated successfully")
      [200, { 'Content-Type' => 'application/json' }, MultiJson.dump(updated_employee)]
    rescue StandardError => e
      Logger.error("Error updating employee: #{e.message}")
      [500, { 'Content-Type' => 'application/json' }, MultiJson.dump({ message: 'Error updating employee', error: e.message })]
    end
  end

  # Delete an employee
  def delete_employee(id)
    begin
      Logger.info("Request to delete employee with ID: #{id}")
      
      deleted = @employee_repository.delete(id)
      
      if !deleted
        Logger.warn("Employee with ID: #{id} not found for deletion")
        return [404, { 'Content-Type' => 'application/json' }, MultiJson.dump({ message: "Employee with ID: #{id} not found" })]
      end
      
      Logger.info("Employee with ID: #{id} deleted successfully")
      [204, { 'Content-Type' => 'application/json' }, '']
    rescue StandardError => e
      Logger.error("Error deleting employee: #{e.message}")
      [500, { 'Content-Type' => 'application/json' }, MultiJson.dump({ message: 'Error deleting employee', error: e.message })]
    end
  end
end
