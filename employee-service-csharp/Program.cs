using EmployeeService.Services;
using Serilog;

var builder = WebApplication.CreateBuilder(args);

// Configure Serilog
Log.Logger = new LoggerConfiguration()
    .WriteTo.Console()
    .WriteTo.File("Logs/employee_service.log", rollingInterval: RollingInterval.Day)
    .CreateLogger();

builder.Host.UseSerilog();

// Add services to the container
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Register services
builder.Services.AddSingleton<IEmployeeService, EmployeeServiceImpl>();

// Configure CORS
builder.Services.AddCors(options =>
{
    options.AddDefaultPolicy(
        policy =>
        {
            policy.AllowAnyOrigin()
                .AllowAnyMethod()
                .AllowAnyHeader();
        });
});

// Configure port
builder.WebHost.UseUrls("http://localhost:8086");

var app = builder.Build();

// Configure the HTTP request pipeline
app.UseSwagger();
app.UseSwaggerUI();

app.UseCors();

app.UseAuthorization();

app.MapControllers();

// Log startup
Log.Information("Employee Service C#/.NET started on http://localhost:8086");

app.Run();
