using System.Net;
using FitnessTrackerAPI.Models;

namespace FitnessTrackerAPI.Middleware
{
    public class ModelValidationMiddleware
    {
        private readonly RequestDelegate _next;

        public ModelValidationMiddleware(RequestDelegate next)
        {
            _next = next;
        }

        public async Task InvokeAsync(HttpContext context)
        {
            await _next(context);

            if (context.Response.StatusCode != (int)HttpStatusCode.BadRequest || context.Response.HasStarted)
            {
                return;
            }

            if (context.Request.Path.StartsWithSegments("/api"))
            {
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
