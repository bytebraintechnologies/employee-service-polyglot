using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace EmployeeService.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class HealthController : ControllerBase
    {
        private readonly ILogger<HealthController> _logger;

        public HealthController(ILogger<HealthController> logger)
        {
            _logger = logger;
        }

        [HttpGet]
        public IActionResult Get()
        {
            _logger.LogInformation("Health check requested");

            var process = Process.GetCurrentProcess();
            var healthData = new
            {
                Status = "UP",
                Service = "employee-service-csharp",
                Time = DateTime.UtcNow,
                System = new
                {
                    OSDescription = RuntimeInformation.OSDescription,
                    FrameworkDescription = RuntimeInformation.FrameworkDescription,
                    ProcessArchitecture = RuntimeInformation.ProcessArchitecture.ToString(),
                    OSArchitecture = RuntimeInformation.OSArchitecture.ToString()
                },
                Resources = new
                {
                    ProcessId = process.Id,
                    ProcessName = process.ProcessName,
                    WorkingSet64 = process.WorkingSet64,
                    VirtualMemorySize64 = process.VirtualMemorySize64,
                    ProcessorCount = Environment.ProcessorCount,
                    TotalMemory = GC.GetTotalMemory(false)
                }
            };

            return Ok(healthData);
        }
    }
}