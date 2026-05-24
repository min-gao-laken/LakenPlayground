using System.Net;
using System.Text.Json;
using FitnessTrackerAPI.Models;
using Microsoft.AspNetCore.Mvc;

namespace FitnessTrackerAPI.Middleware
{
    public class ModelValidationMiddleware
    {
        private readonly RequestDelegate _next;
        private readonly ILogger<ModelValidationMiddleware> _logger;

        public ModelValidationMiddleware(RequestDelegate next, ILogger<ModelValidationMiddleware> logger)
        {
            _next = next;
            _logger = logger;
        }

        public async Task InvokeAsync(HttpContext context)
        {
            // 在执行管道之前，检查是否有验证错误
            await _next(context);

            // 如果响应是 400 Bad Request 且是模型验证失败
            if (context.Response.StatusCode == (int)HttpStatusCode.BadRequest)
            {
                // 检查是否已有响应体
                if (context.Response.HasStarted)
                    return;

                // 重写为统一的验证错误格式
                context.Response.ContentType = "application/json";
                var response = new ErrorResponse(
                    (int)HttpStatusCode.BadRequest,
                    "Model validation failed",
                    "Please check the request parameters"
                );

                await context.Response.WriteAsJsonAsync(response);
            }
        }
    }
}
