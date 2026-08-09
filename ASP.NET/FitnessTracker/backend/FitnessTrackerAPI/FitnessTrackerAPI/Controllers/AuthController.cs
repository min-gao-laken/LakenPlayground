using System.Text;
using System.Text.Json;
using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace FitnessTrackerAPI.Controllers
{
    /// <summary>
    /// Authentication and user management endpoints
    /// </summary>
    [ApiController]
    [Route("api/auth")]
    public class AuthController : ControllerBase
    {
        private readonly IAuthService _authService;

        public AuthController(IAuthService authService)
        {
            _authService = authService;
        }

        /// <summary>
        /// Register a new user account
        /// </summary>
        /// <param name="ct">Cancellation token</param>
        /// <returns>Authentication response with JWT token</returns>
        /// <response code="200">User registered successfully</response>
        /// <response code="400">Username already exists or invalid credentials</response>
        [HttpPost("register")]
        [AllowAnonymous]
        [Consumes("application/json")]
        [ProducesResponseType(typeof(AuthResponseDto), StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult<AuthResponseDto>> Register([FromBody] RegisterRequestDto request, CancellationToken ct)
        {
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

        /// <summary>
        /// Login with existing credentials
        /// </summary>
        /// <param name="ct">Cancellation token</param>
        /// <returns>Authentication response with JWT token</returns>
        /// <response code="200">Login successful</response>
        /// <response code="401">Invalid username or password</response>
        /// <response code="400">Invalid request body</response>
        [HttpPost("login")]
        [AllowAnonymous]
        [Consumes("application/json")]
        [ProducesResponseType(typeof(AuthResponseDto), StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult<AuthResponseDto>> Login([FromBody] LoginRequestDto request, CancellationToken ct)
        {
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

        /// <summary>
        /// Get current authenticated user information
        /// </summary>
        /// <returns>Current user details</returns>
        /// <response code="200">User information retrieved successfully</response>
        /// <response code="401">User not authenticated</response>
        [Authorize]
        [HttpGet("me")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        public ActionResult<object> Me()
        {
            return Ok(new
            {
                username = User.Identity?.Name,
                userId = User.FindFirst(System.Security.Claims.ClaimTypes.NameIdentifier)?.Value
            });
        }

    }
}
