require_relative 'employee'
require 'logger'

class EmployeeRepository
  def initialize(logger)
    @employees = {}
    @logger = logger
  end
  
  def find_all
    @logger.info "Finding all employees, total count: #{@employees.size}"
    @employees.values
  end
  
  def find_by_id(id)
    @logger.info "Finding employee by ID: #{id}"
    @employees[id]
  end
  
  def save(employee)
    @logger.info "Saving employee: #{employee.to_h}"
    @employees[employee.id] = employee
    employee
  end
  
  def update(id, employee)
    @logger.info "Updating employee with ID: #{id}"
    if @employees.key?(id)
      employee.id = id
      @employees[id] = employee
      employee
    else
      @logger.warn "Attempted to update non-existent employee with ID: #{id}"
      nil
    end
  end
  
  def delete(id)
    @logger.info "Deleting employee with ID: #{id}"
    @employees.delete(id)
  end
  
  def add_sample_data
    @logger.info "Adding sample data to employee repository"
    
    sample_employees = [
      {
        first_name: "John",
        last_name: "Doe",
        email: "john.doe@example.com",
        phone: "555-123-4567",
        position: "Software Engineer",
        department: "Engineering",
        salary: 85000.00,
        hire_date: "2020-03-15"
      },
      {
        first_name: "Jane",
        last_name: "Smith",
        email: "jane.smith@example.com",
        phone: "555-987-6543",
        position: "Product Manager",
        department: "Product",
        salary: 95000.00,
        hire_date: "2019-07-10"
      },
      {
        first_name: "Michael",
        last_name: "Johnson",
        email: "michael.johnson@example.com",
        phone: "555-234-5678",
        position: "UX Designer",
        department: "Design",
        salary: 78000.00,
        hire_date: "2021-01-05"
      }
    ]
    
    sample_employees.each do |data|
      employee = Employee.new(data)
      save(employee)
    end
  end
end
