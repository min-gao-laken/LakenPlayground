using System.Net;
using System.Text.Json;
using FitnessTrackerAPI.Models;
using Microsoft.AspNetCore.Mvc;

namespace FitnessTrackerAPI.Middleware
{
    public class ModelValidationMiddleware
    {
        private readonly RequestDelegate _next;

        public ModelValidationMiddleware(RequestDelegate next, ILogger<ModelValidationMiddleware> logger)
        {
            _next = next;
        }

        public async Task InvokeAsync(HttpContext context)
        {
            await _next(context);

            // if the response status code is 400 and the response body contains model validation errors
            if (context.Response.StatusCode == (int)HttpStatusCode.BadRequest)
            {
                // check if the response has already started
                if (context.Response.HasStarted)
                    return;

                // set the response type to the custom error response format
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
