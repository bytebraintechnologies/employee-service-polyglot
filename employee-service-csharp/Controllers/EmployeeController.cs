using EmployeeService.Models;
using EmployeeService.Services;
using Microsoft.AspNetCore.Mvc;

namespace EmployeeService.Controllers
{
    [ApiController]
    [Route("api/[controller]s")]
    public class EmployeeController : ControllerBase
    {
        private readonly IEmployeeService _employeeService;
        private readonly ILogger<EmployeeController> _logger;

        public EmployeeController(IEmployeeService employeeService, ILogger<EmployeeController> logger)
        {
            _employeeService = employeeService;
            _logger = logger;
        }

        /// <summary>
        /// Get all employees
        /// </summary>
        /// <returns>List of all employees</returns>
        [HttpGet]
        public ActionResult<IEnumerable<Employee>> GetAllEmployees()
        {
            try
            {
                _logger.LogInformation("Request to get all employees");
                var employees = _employeeService.GetAllEmployees();
                _logger.LogInformation("Retrieved {Count} employees successfully", employees.Count());
                return Ok(employees);
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error retrieving employees");
                return StatusCode(500, new { message = "Error retrieving employees", error = ex.Message });
            }
        }

        /// <summary>
        /// Get employee by ID
        /// </summary>
        /// <param name="id">Employee ID</param>
        /// <returns>Employee if found</returns>
        [HttpGet("{id}")]
        public ActionResult<Employee> GetEmployeeById(string id)
        {
            try
            {
                _logger.LogInformation("Request to get employee with ID: {Id}", id);

                var employee = _employeeService.GetEmployeeById(id);

                if (employee == null)
                {
                    _logger.LogWarning("Employee with ID: {Id} not found", id);
                    return NotFound(new { message = $"Employee with ID: {id} not found" });
                }

                _logger.LogInformation("Retrieved employee with ID: {Id} successfully", id);
                return Ok(employee);
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error retrieving employee");
                return StatusCode(500, new { message = "Error retrieving employee", error = ex.Message });
            }
        }

        /// <summary>
        /// Create a new employee
        /// </summary>
        /// <param name="employee">Employee data</param>
        /// <returns>Created employee</returns>
        [HttpPost]
        public ActionResult<Employee> CreateEmployee(Employee employee)
        {
            try
            {
                _logger.LogInformation("Request to create new employee");

                // ModelState validation is handled by [ApiController]
                if (!ModelState.IsValid)
                {
                    _logger.LogWarning("Failed to create employee: Invalid model state");
                    return BadRequest(ModelState);
                }

                var newEmployee = _employeeService.CreateEmployee(employee);
                _logger.LogInformation("Employee created successfully with ID: {Id}", newEmployee.Id);

                return CreatedAtAction(nameof(GetEmployeeById), new { id = newEmployee.Id }, newEmployee);
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error creating employee");
                return StatusCode(500, new { message = "Error creating employee", error = ex.Message });
            }
        }

        /// <summary>
        /// Update an employee
        /// </summary>
        /// <param name="id">Employee ID</param>
        /// <param name="employee">Updated employee data</param>
        /// <returns>Updated employee</returns>
        [HttpPut("{id}")]
        public ActionResult<Employee> UpdateEmployee(string id, Employee employee)
        {
            try
            {
                _logger.LogInformation("Request to update employee with ID: {Id}", id);

                var updatedEmployee = _employeeService.UpdateEmployee(id, employee);

                if (updatedEmployee == null)
                {
                    _logger.LogWarning("Employee with ID: {Id} not found for update", id);
                    return NotFound(new { message = $"Employee with ID: {id} not found" });
                }

                _logger.LogInformation("Employee with ID: {Id} updated successfully", id);
                return Ok(updatedEmployee);
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error updating employee");
                return StatusCode(500, new { message = "Error updating employee", error = ex.Message });
            }
        }

        /// <summary>
        /// Delete an employee
        /// </summary>
        /// <param name="id">Employee ID</param>
        /// <returns>No content if successful</returns>
        [HttpDelete("{id}")]
        public ActionResult DeleteEmployee(string id)
        {
            try
            {
                _logger.LogInformation("Request to delete employee with ID: {Id}", id);

                var deleted = _employeeService.DeleteEmployee(id);

                if (!deleted)
                {
                    _logger.LogWarning("Employee with ID: {Id} not found for deletion", id);
                    return NotFound(new { message = $"Employee with ID: {id} not found" });
                }

                _logger.LogInformation("Employee with ID: {Id} deleted successfully", id);
                return NoContent();
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error deleting employee");
                return StatusCode(500, new { message = "Error deleting employee", error = ex.Message });
            }
        }
    }
}
