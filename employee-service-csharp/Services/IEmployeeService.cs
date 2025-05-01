using EmployeeService.Models;

namespace EmployeeService.Services
{
    /// <summary>
    /// Interface for employee service
    /// </summary>
    public interface IEmployeeService
    {
        /// <summary>
        /// Get all employees
        /// </summary>
        /// <returns>List of all employees</returns>
        IEnumerable<Employee> GetAllEmployees();

        /// <summary>
        /// Get employee by ID
        /// </summary>
        /// <param name="id">Employee ID</param>
        /// <returns>Employee if found, otherwise null</returns>
        Employee? GetEmployeeById(string id);

        /// <summary>
        /// Create a new employee
        /// </summary>
        /// <param name="employee">Employee data</param>
        /// <returns>Created employee</returns>
        Employee CreateEmployee(Employee employee);

        /// <summary>
        /// Update an existing employee
        /// </summary>
        /// <param name="id">Employee ID</param>
        /// <param name="employeeData">Updated employee data</param>
        /// <returns>Updated employee if found, otherwise null</returns>
        Employee? UpdateEmployee(string id, Employee employeeData);

        /// <summary>
        /// Delete an employee
        /// </summary>
        /// <param name="id">Employee ID</param>
        /// <returns>True if deleted, false if not found</returns>
        bool DeleteEmployee(string id);
    }
}
