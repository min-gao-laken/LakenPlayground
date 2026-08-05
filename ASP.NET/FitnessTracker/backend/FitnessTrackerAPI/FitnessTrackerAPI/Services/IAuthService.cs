using FitnessTrackerAPI.DTOs;

namespace FitnessTrackerAPI.Services
{
    public interface IAuthService
    {
        Task<AuthResponseDto?> LoginAsync(string username, string password, CancellationToken ct = default);
        Task<AuthResponseDto?> RegisterAsync(string username, string password, CancellationToken ct = default);
    }
}
