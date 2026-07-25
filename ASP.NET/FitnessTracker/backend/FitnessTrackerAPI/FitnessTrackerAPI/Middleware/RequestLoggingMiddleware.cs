using System.Diagnostics;

namespace FitnessTrackerAPI.Middleware
{
    public class RequestLoggingMiddleware
    {
        private readonly RequestDelegate _next;

        public RequestLoggingMiddleware(RequestDelegate next)
        {
            _next = next;
            Console.WriteLine("RequestLoggingMiddleware was created");
        }

        public async Task InvokeAsync(HttpContext context)
        {
            Console.WriteLine($"Method: {context.Request.Method}");
            Console.WriteLine($"Path: {context.Request.Path}");

            await _next(context); // Call the next middleware in the pipeline
            Console.WriteLine("Request finished");
        }
    }
}
