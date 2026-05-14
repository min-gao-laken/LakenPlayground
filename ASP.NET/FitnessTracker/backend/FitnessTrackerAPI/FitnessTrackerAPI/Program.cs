using FitnessTrackerAPI.Data;
using FitnessTrackerAPI.Middleware;
using Microsoft.EntityFrameworkCore;
using System.Text.Json.Serialization;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllers().AddJsonOptions(options =>
{
    // Handle circular references in JSON serialization
    options.JsonSerializerOptions.ReferenceHandler = ReferenceHandler.IgnoreCycles;
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

// ⭐ Swagger
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// ⭐ 中间件执行顺序很重要！
// 1. 先注册异常处理中间件（捕获所有异常）
app.UseMiddleware<ExceptionHandlingMiddleware>();
// 2. 再注册请求日志中间件
app.UseMiddleware<RequestLoggingMiddleware>();

// ⭐ Swagger UI
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
