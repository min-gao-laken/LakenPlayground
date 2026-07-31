using FitnessTrackerAPI.Data;
using FitnessTrackerAPI.Middleware;
using Microsoft.EntityFrameworkCore;
using System.Text.Json.Serialization;

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

app.UseAuthorization();

app.MapControllers();

using (var scope = app.Services.CreateScope())
{
    var dbContext = scope.ServiceProvider.GetRequiredService<AppDbContext>();

    try
    {
        var canConnect = await dbContext.Database.CanConnectAsync();
        if (!canConnect)
        {
            await dbContext.Database.EnsureCreatedAsync();
        }
    }
    catch
    {
        await dbContext.Database.EnsureCreatedAsync();
    }

    await dbContext.Database.MigrateAsync();

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

app.Run();
