using System.Text;
using System.Text.Json;
using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace FitnessTrackerAPI.Controllers
{
    [ApiController]
    [Route("api/auth")]
    public class AuthController : ControllerBase
    {
        private readonly IAuthService _authService;

        public AuthController(IAuthService authService)
        {
            _authService = authService;
        }

        [HttpPost("register")]
        [Consumes("application/json")]
        public async Task<ActionResult<AuthResponseDto>> Register(CancellationToken ct)
        {
            var request = await ReadJsonBodyAsync<RegisterRequestDto>();
            if (request == null)
            {
                return BadRequest(new { message = "Invalid JSON body" });
            }

            var result = await _authService.RegisterAsync(request.Username, request.Password, ct);
            if (result == null)
            {
                return BadRequest(new { message = "Username already exists or credentials are invalid" });
            }

            return Ok(result);
        }

        [HttpPost("login")]
        [Consumes("application/json")]
        public async Task<ActionResult<AuthResponseDto>> Login(CancellationToken ct)
        {
            var request = await ReadJsonBodyAsync<LoginRequestDto>();
            if (request == null)
            {
                return BadRequest(new { message = "Invalid JSON body" });
            }

            var result = await _authService.LoginAsync(request.Username, request.Password, ct);
            if (result == null)
            {
                return Unauthorized(new { message = "Invalid username or password" });
            }

            return Ok(result);
        }

        [Authorize]
        [HttpGet("me")]
        public ActionResult<object> Me()
        {
            return Ok(new
            {
                username = User.Identity?.Name,
                userId = User.FindFirst(System.Security.Claims.ClaimTypes.NameIdentifier)?.Value
            });
        }

        private async Task<T?> ReadJsonBodyAsync<T>()
        {
            try
            {
                Request.EnableBuffering();
                using var reader = new StreamReader(Request.Body, Encoding.UTF8, leaveOpen: true);
                var body = await reader.ReadToEndAsync();
                Request.Body.Position = 0;

                Console.WriteLine($"Raw auth body: {body}");

                return JsonSerializer.Deserialize<T>(body, new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                });
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Auth JSON parse error: {ex}");
                return default;
            }
        }

    }
}
