using FitnessTrackerAPI.Data;
using FitnessTrackerAPI.Middleware;
using FitnessTrackerAPI.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json.Serialization;
using System.Data.Common;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllers()
    .AddJsonOptions(options =>
    {
        // Handle circular references in JSON serialization
        options.JsonSerializerOptions.ReferenceHandler = ReferenceHandler.IgnoreCycles;
    })
    .ConfigureApiBehaviorOptions(options =>
    {
        // Enable automatic model validation
        options.SuppressModelStateInvalidFilter = false;

        // Customize the model validation error response
        options.InvalidModelStateResponseFactory = context =>
        {
            var errors = context.ModelState
                .Where(e => e.Value?.Errors.Count > 0)
                .ToDictionary(
                    kvp => kvp.Key,
                    kvp => kvp.Value?.Errors.Select(e => e.ErrorMessage).ToArray() ?? Array.Empty<string>()
                );

            return new Microsoft.AspNetCore.Mvc.BadRequestObjectResult(
                new
                {
                    statusCode = 400,
                    message = "Model validation failed",
                    errors = errors,
                    timestamp = DateTime.UtcNow
                }
            );
        };
    });

// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddOpenApi();

// Configure Entity Framework Core with SQL Server
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

// Register repository and service
builder.Services.AddScoped<FitnessTrackerAPI.Repositories.IWorkoutRepository, FitnessTrackerAPI.Repositories.WorkoutRepository>();
builder.Services.AddScoped<FitnessTrackerAPI.Services.IWorkoutService, FitnessTrackerAPI.Services.WorkoutService>();
builder.Services.AddScoped<FitnessTrackerAPI.Repositories.IExerciseRepository, FitnessTrackerAPI.Repositories.ExerciseRepository>();
builder.Services.AddScoped<FitnessTrackerAPI.Services.IExerciseService, FitnessTrackerAPI.Services.ExerciseService>();
builder.Services.AddScoped<FitnessTrackerAPI.Repositories.ISetRecordRepository, FitnessTrackerAPI.Repositories.SetRecordRepository>();
builder.Services.AddScoped<FitnessTrackerAPI.Services.ISetRecordService, FitnessTrackerAPI.Services.SetRecordService>();
builder.Services.AddScoped<IAuthService, AuthService>();

var jwtKey = builder.Configuration["Jwt:Key"] ?? "dev-secret-key-please-change";
var issuer = builder.Configuration["Jwt:Issuer"] ?? "FitnessTrackerAPI";
var audience = builder.Configuration["Jwt:Audience"] ?? "FitnessTrackerAPI";
var signingKeyBytes = SHA256.HashData(Encoding.UTF8.GetBytes(jwtKey));

builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
    .AddJwtBearer(options =>
    {
        options.TokenValidationParameters = new TokenValidationParameters
        {
            ValidateIssuer = true,
            ValidateAudience = true,
            ValidateLifetime = true,
            ValidateIssuerSigningKey = true,
            ValidIssuer = issuer,
            ValidAudience = audience,
            IssuerSigningKey = new SymmetricSecurityKey(signingKeyBytes)
        };
    });

builder.Services.AddAuthorization();

// Swagger
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// the order of middleware execution matters!
// the request logging middleware should be registered first to log all incoming requests
app.UseMiddleware<RequestLoggingMiddleware>();
// the exception handling middleware should be registered next to catch any exceptions thrown by subsequent middleware
app.UseMiddleware<ExceptionHandlingMiddleware>();
app.UseMiddleware<ModelValidationMiddleware>();

// Swagger UI
app.UseSwagger(c => c.RouteTemplate = "swagger/{documentName}/swagger.json");
app.UseSwaggerUI(c =>
{
    c.SwaggerEndpoint("/swagger/v1/swagger.json", "FitnessTracker API V1");
    c.RoutePrefix = "swagger";
});
app.MapOpenApi();

app.MapGet("/health", () => Results.Ok(new { status = "ok" }));
app.MapGet("/hello", () => Results.Text("hello from container"));

if (!app.Environment.IsDevelopment())
{
    app.UseHttpsRedirection();
}

app.UseAuthentication();
app.UseAuthorization();

app.MapControllers();

using (var scope = app.Services.CreateScope())
{
    var dbContext = scope.ServiceProvider.GetRequiredService<AppDbContext>();
    var connection = dbContext.Database.GetDbConnection();
    var connectionString = connection.ConnectionString;

    Console.WriteLine($"Initializing database with connection: {connectionString}");

    try
    {
        if (!await dbContext.Database.CanConnectAsync())
        {
            Console.WriteLine("Database is not reachable; attempting to create it.");
            await dbContext.Database.EnsureCreatedAsync();
        }

        await dbContext.Database.MigrateAsync();
        Console.WriteLine("Database migration completed successfully.");
    }
    catch (Exception ex)
    {
        Console.WriteLine($"Database initialization warning: {ex.Message}");
        try
        {
            await dbContext.Database.EnsureCreatedAsync();
            Console.WriteLine("Database EnsureCreated completed.");
        }
        catch (Exception ensureEx)
        {
            Console.WriteLine($"Database EnsureCreated failed: {ensureEx.Message}");
        }
    }

    try
    {
        if (!await dbContext.Workouts.AnyAsync())
        {
            var workout = new FitnessTrackerAPI.Models.Workout
            {
                Date = DateTime.UtcNow,
                Notes = "Seeded workout"
            };

            dbContext.Workouts.Add(workout);
            await dbContext.SaveChangesAsync();

            dbContext.Exercises.Add(new FitnessTrackerAPI.Models.Exercise
            {
                Name = "Seeded Exercise",
                WorkoutId = workout.Id
            });

            await dbContext.SaveChangesAsync();
        }
    }
    catch (Exception seedEx)
    {
        Console.WriteLine($"Seeding warning: {seedEx.Message}");
    }
}

app.Run();
