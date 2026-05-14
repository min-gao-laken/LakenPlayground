using System.Diagnostics;

namespace FitnessTrackerAPI.Middleware
{
    public class RequestLoggingMiddleware
    {
        private readonly RequestDelegate _next;

        public RequestLoggingMiddleware(RequestDelegate next)
        {
            _next = next;
            Console.WriteLine("[构造函数] RequestLoggingMiddleware 被创建");
        }

        public async Task InvokeAsync(HttpContext context)
        {
            Console.WriteLine($"[InvokeAsync] RequestLoggingMiddleware 记录请求: {context.Request.Path}");
            await _next(context);  // 继续传递给控制器或下一个中间件
            Console.WriteLine("[InvokeAsync] RequestLoggingMiddleware 响应处理完成");
        }
    }
}
