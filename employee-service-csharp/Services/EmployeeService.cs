using EmployeeService.Models;
using System.Collections.Concurrent;

namespace EmployeeService.Services
{
    /// <summary>
    /// Implementation of employee service
    /// </summary>
    public class EmployeeServiceImpl : IEmployeeService
    {
        private readonly ILogger<EmployeeServiceImpl> _logger;
        private readonly ConcurrentDictionary<string, Employee> _employees = new();

        /// <summary>
        /// Constructor with dependency injection
        /// </summary>
        /// <param name="logger">Logger</param>
        public EmployeeServiceImpl(ILogger<EmployeeServiceImpl> logger)
        {
            _logger = logger;
            InitializeData();
        }

        /// <summary>
        /// Initialize in-memory database with sample data
        /// </summary>
        private void InitializeData()
        {
            var sampleEmployees = new List<Employee>
            {
                new Employee
                {
                    Id = "1",
                    FirstName = "John",
                    LastName = "Doe",
                    Email = "john.doe@example.com",
                    Department = "IT",
                    Salary = 75000.0m
                },
                new Employee
                {
                    Id = "2",
                    FirstName = "Jane",
                    LastName = "Smith",
                    Email = "jane.smith@example.com",
                    Department = "HR",
                    Salary = 65000.0m
                },
                new Employee
                {
                    Id = "3",
                    FirstName = "Michael",
                    LastName = "Brown",
                    Email = "michael.brown@example.com",
                    Department = "Finance",
                    Salary = 85000.0m
                },
                new Employee
                {
                    Id = "4",
                    FirstName = "Sarah",
                    LastName = "Johnson",
                    Email = "sarah.johnson@example.com",
                    Department = "Marketing",
                    Salary = 70000.0m
                },
                new Employee
                {
                    Id = "5",
                    FirstName = "David",
                    LastName = "Williams",
                    Email = "david.williams@example.com",
                    Department = "Operations",
                    Salary = 72000.0m
                }
            };

            foreach (var employee in sampleEmployees)
            {
                _employees.TryAdd(employee.Id, employee);
            }

            _logger.LogInformation("Initialized in-memory employee database with sample data");
        }

        /// <inheritdoc/>
        public IEnumerable<Employee> GetAllEmployees()
        {
            _logger.LogDebug("Retrieving all employees from in-memory database");
            return _employees.Values.ToList();
        }

        /// <inheritdoc/>
        public Employee? GetEmployeeById(string id)
        {
            _logger.LogDebug("Finding employee with ID: {Id}", id);
            _employees.TryGetValue(id, out var employee);
            return employee;
        }

        /// <inheritdoc/>
        public Employee CreateEmployee(Employee employee)
        {
            employee.Id = Guid.NewGuid().ToString();
            _employees.TryAdd(employee.Id, employee);
            _logger.LogInformation("Created new employee with ID: {Id}", employee.Id);
            return employee;
        }

        /// <inheritdoc/>
        public Employee? UpdateEmployee(string id, Employee employeeData)
        {
            _logger.LogDebug("Attempting to update employee with ID: {Id}", id);

            if (!_employees.TryGetValue(id, out var existingEmployee))
            {
                _logger.LogWarning("Employee with ID: {Id} not found", id);
                return null;
            }

            var updatedEmployee = new Employee
            {
                Id = id,
                FirstName = employeeData.FirstName,
                LastName = employeeData.LastName,
                Email = employeeData.Email,
                Department = employeeData.Department,
                Salary = employeeData.Salary
            };

            _employees[id] = updatedEmployee;
            _logger.LogInformation("Successfully updated employee with ID: {Id}", id);

            return updatedEmployee;
        }

        /// <inheritdoc/>
        public bool DeleteEmployee(string id)
        {
            _logger.LogDebug("Attempting to delete employee with ID: {Id}", id);

            var deleted = _employees.TryRemove(id, out _);

            if (deleted)
            {
                _logger.LogInformation("Successfully deleted employee with ID: {Id}", id);
            }
            else
            {
                _logger.LogWarning("Employee with ID: {Id} not found for deletion", id);
            }

            return deleted;
        }
    }
}
