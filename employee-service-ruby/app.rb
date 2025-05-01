require 'sinatra'
require 'multi_json'
require_relative 'controllers/employee_controller'
require_relative 'config/logger'

# Configure Sinatra
set :port, 8085
set :bind, '0.0.0.0'

# Configure CORS
before do
  response.headers['Access-Control-Allow-Origin'] = '*'
  response.headers['Access-Control-Allow-Methods'] = 'GET, POST, PUT, DELETE, OPTIONS'
  response.headers['Access-Control-Allow-Headers'] = 'Content-Type, Accept'
end

options '*' do
  response.headers['Allow'] = 'GET, POST, PUT, DELETE, OPTIONS'
  status 200
end

# Initialize controller
employee_controller = EmployeeController.new

# Parse JSON request body
before do
  if request.content_type == 'application/json'
    request.body.rewind
    @request_payload = MultiJson.load(request.body.read) rescue {}
  end
end

# Log request
before do
  Logger.info("#{request.request_method} #{request.path_info}")
end

# API endpoints
# Get all employees
get '/api/employees' do
  status, headers, body = employee_controller.get_all_employees
  status status
  headers.each { |key, value| response[key] = value }
  body
end

# Get employee by ID
get '/api/employees/:id' do
  status, headers, body = employee_controller.get_employee_by_id(params['id'])
  status status
  headers.each { |key, value| response[key] = value }
  body
end

# Create employee
post '/api/employees' do
  status, headers, body = employee_controller.create_employee(@request_payload)
  status status
  headers.each { |key, value| response[key] = value }
  body
end

# Update employee
put '/api/employees/:id' do
  status, headers, body = employee_controller.update_employee(params['id'], @request_payload)
  status status
  headers.each { |key, value| response[key] = value }
  body
end

# Delete employee
delete '/api/employees/:id' do
  status, headers, body = employee_controller.delete_employee(params['id'])
  status status
  headers.each { |key, value| response[key] = value }
  body
end

# Health check endpoint
get '/health' do
  content_type :json
  MultiJson.dump({ status: 'UP', service: 'Employee Service Ruby' })
end

# Startup message
Logger.info("Employee Service Ruby started on http://localhost:#{settings.port}")
