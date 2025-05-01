using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace EmployeeService.Models
{
    /// <summary>
    /// Employee model class
    /// </summary>
    public class Employee
    {
        [JsonPropertyName("id")]
        public string Id { get; set; } = string.Empty;

        [Required(ErrorMessage = "First name is required")]
        [JsonPropertyName("firstName")]
        public string FirstName { get; set; } = string.Empty;

        [Required(ErrorMessage = "Last name is required")]
        [JsonPropertyName("lastName")]
        public string LastName { get; set; } = string.Empty;

        [Required(ErrorMessage = "Email is required")]
        [EmailAddress(ErrorMessage = "Invalid email address")]
        [JsonPropertyName("email")]
        public string Email { get; set; } = string.Empty;

        [JsonPropertyName("department")]
        public string Department { get; set; } = string.Empty;

        [JsonPropertyName("salary")]
        public decimal Salary { get; set; }
    }
}
