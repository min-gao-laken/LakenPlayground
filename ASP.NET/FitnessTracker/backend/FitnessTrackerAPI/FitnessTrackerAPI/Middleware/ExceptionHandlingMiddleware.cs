using System.Net;
using FitnessTrackerAPI.Models;

namespace FitnessTrackerAPI.Middleware
{
    public class ExceptionHandlingMiddleware
    {
        private readonly RequestDelegate _next;
        private readonly ILogger<ExceptionHandlingMiddleware> _logger;

        public ExceptionHandlingMiddleware(RequestDelegate next, ILogger<ExceptionHandlingMiddleware> logger)
        {
            _next = next;
            _logger = logger;
        }

        public async Task InvokeAsync(HttpContext context)
        {
            try
            {
                Console.WriteLine("in the ExceptionHandlingMiddleware...");
                await _next(context);
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "An unhandled exception has occurred");
                await HandleExceptionAsync(context, ex);
            }
        }

        private static Task HandleExceptionAsync(HttpContext context, Exception exception)
        {
            context.Response.ContentType = "application/json";

            ErrorResponse response;

            switch (exception)
            {
                case KeyNotFoundException ex:
                    context.Response.StatusCode = (int)HttpStatusCode.NotFound;
                    response = new ErrorResponse(
                        (int)HttpStatusCode.NotFound,
                        "Resource Not Found",
                        ex.Message
                    );
                    break;

                case ArgumentException ex:
                    context.Response.StatusCode = (int)HttpStatusCode.BadRequest;
                    response = new ErrorResponse(
                        (int)HttpStatusCode.BadRequest,
                        "Invalid Request Parameter",
                        ex.Message
                    );
                    break;

                case InvalidOperationException ex:
                    context.Response.StatusCode = (int)HttpStatusCode.BadRequest;
                    response = new ErrorResponse(
                        (int)HttpStatusCode.BadRequest,
                        "Invalid Operation",
                        ex.Message
                    );
                    break;

                default:
                    context.Response.StatusCode = (int)HttpStatusCode.InternalServerError;
                    response = new ErrorResponse(
                        (int)HttpStatusCode.InternalServerError,
                        "Internal Server Error",
                        exception.Message
                    );
                    break;
            }

            return context.Response.WriteAsJsonAsync(response);
        }
    }
}
