require 'json'
require 'uuid'
require 'date'

class Employee
  attr_accessor :id, :first_name, :last_name, :email, :phone, :position, :department, :salary, :hire_date
  
  def initialize(attrs = {})
    @id = attrs[:id] || UUID.new.generate
    @first_name = attrs[:first_name]
    @last_name = attrs[:last_name]
    @email = attrs[:email]
    @phone = attrs[:phone]
    @position = attrs[:position]
    @department = attrs[:department]
    @salary = attrs[:salary].to_f
    @hire_date = attrs[:hire_date] || Date.today.to_s
  end
  
  def to_h
    {
      id: @id,
      first_name: @first_name,
      last_name: @last_name,
      email: @email,
      phone: @phone,
      position: @position,
      department: @department,
      salary: @salary,
      hire_date: @hire_date
    }
  end
  
  def to_json(*_args)
    to_h.to_json
  end
  
  def self.from_json(json_str)
    data = JSON.parse(json_str, symbolize_names: true)
    new(data)
  end
end
