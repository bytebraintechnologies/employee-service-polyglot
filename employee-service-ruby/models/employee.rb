require 'securerandom'
require_relative '../config/logger'

# Employee model class for managing employee data
class Employee
  attr_reader :id, :first_name, :last_name, :email, :department, :salary

  def initialize(id, first_name, last_name, email, department, salary)
    @id = id || SecureRandom.uuid
    @first_name = first_name
    @last_name = last_name
    @email = email
    @department = department
    @salary = salary
  end

  # Convert employee object to hash
  def to_hash
    {
      id: @id,
      firstName: @first_name,
      lastName: @last_name,
      email: @email,
      department: @department,
      salary: @salary
    }
  end
end

# Employee repository for CRUD operations
class EmployeeRepository
  def initialize
    # Initialize in-memory database with sample data
    @employees = [
      Employee.new('1', 'John', 'Doe', 'john.doe@example.com', 'IT', 75000.0),
      Employee.new('2', 'Jane', 'Smith', 'jane.smith@example.com', 'HR', 65000.0),
      Employee.new('3', 'Michael', 'Brown', 'michael.brown@example.com', 'Finance', 85000.0),
      Employee.new('4', 'Sarah', 'Johnson', 'sarah.johnson@example.com', 'Marketing', 70000.0),
      Employee.new('5', 'David', 'Williams', 'david.williams@example.com', 'Operations', 72000.0)
    ]
    
    Logger.info('Initialized in-memory employee database with sample data')
  end

  # Get all employees
  def find_all
    Logger.debug('Retrieving all employees from in-memory database')
    @employees.map(&:to_hash)
  end

  # Find employee by ID
  def find_by_id(id)
    Logger.debug("Finding employee with ID: #{id}")
    employee = @employees.find { |emp| emp.id == id }
    employee&.to_hash
  end

  # Create a new employee
  def create(data)
    employee = Employee.new(
      nil,
      data['firstName'],
      data['lastName'],
      data['email'],
      data['department'],
      data['salary']
    )
    
    @employees << employee
    Logger.info("Created new employee with ID: #{employee.id}")
    
    employee.to_hash
  end

  # Update an existing employee
  def update(id, data)
    Logger.debug("Attempting to update employee with ID: #{id}")
    
    index = @employees.find_index { |emp| emp.id == id }
    return nil if index.nil?
    
    current = @employees[index]
    employee = Employee.new(
      id,
      data['firstName'] || current.first_name,
      data['lastName'] || current.last_name,
      data['email'] || current.email,
      data['department'] || current.department,
      data['salary'] || current.salary
    )
    
    @employees[index] = employee
    Logger.info("Successfully updated employee with ID: #{id}")
    
    employee.to_hash
  end

  # Delete an employee
  def delete(id)
    Logger.debug("Attempting to delete employee with ID: #{id}")
    
    initial_size = @employees.size
    @employees.reject! { |emp| emp.id == id }
    
    deleted = @employees.size < initial_size
    
    if deleted
      Logger.info("Successfully deleted employee with ID: #{id}")
    else
      Logger.warn("Employee with ID: #{id} not found for deletion")
    end
    
    deleted
  end
end
