using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;
using FitnessTrackerAPI.Data;
using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;

namespace FitnessTrackerAPI.Services
{
    public class AuthService : IAuthService
    {
        private readonly AppDbContext _dbContext;
        private readonly IConfiguration _configuration;

        public AuthService(AppDbContext dbContext, IConfiguration configuration)
        {
            _dbContext = dbContext;
            _configuration = configuration;
        }

        public async Task<AuthResponseDto?> LoginAsync(string username, string password, CancellationToken ct = default)
        {
            var user = await _dbContext.Users.FirstOrDefaultAsync(u => u.Username == username, ct);
            if (user == null || !VerifyPassword(password, user.PasswordHash))
            {
                return null;
            }

            return CreateToken(user);
        }

        public async Task<AuthResponseDto?> RegisterAsync(string username, string password, CancellationToken ct = default)
        {
            if (string.IsNullOrWhiteSpace(username) || string.IsNullOrWhiteSpace(password))
            {
                return null;
            }

            var exists = await _dbContext.Users.AnyAsync(u => u.Username == username, ct);
            if (exists)
            {
                return null;
            }

            var user = new User
            {
                Username = username,
                PasswordHash = HashPassword(password)
            };

            _dbContext.Users.Add(user);
            await _dbContext.SaveChangesAsync(ct);

            return CreateToken(user);
        }

        private AuthResponseDto CreateToken(User user)
        {
            var jwtKey = _configuration["Jwt:Key"] ?? "dev-secret-key-please-change";
            var issuer = _configuration["Jwt:Issuer"] ?? "FitnessTrackerAPI";
            var audience = _configuration["Jwt:Audience"] ?? "FitnessTrackerAPI";
            var expiresMinutes = int.TryParse(_configuration["Jwt:ExpiresMinutes"], out var minutes) ? minutes : 60;
            var signingKeyBytes = SHA256.HashData(Encoding.UTF8.GetBytes(jwtKey));

            var securityKey = new SymmetricSecurityKey(signingKeyBytes);
            var credentials = new SigningCredentials(securityKey, SecurityAlgorithms.HmacSha256);

            var claims = new[]
            {
                new Claim(JwtRegisteredClaimNames.Sub, user.Id.ToString()),
                new Claim(ClaimTypes.Name, user.Username),
                new Claim(ClaimTypes.NameIdentifier, user.Id.ToString())
            };

            var token = new JwtSecurityToken(
                issuer: issuer,
                audience: audience,
                claims: claims,
                expires: DateTime.UtcNow.AddMinutes(expiresMinutes),
                signingCredentials: credentials);

            return new AuthResponseDto
            {
                Token = new JwtSecurityTokenHandler().WriteToken(token),
                Username = user.Username
            };
        }

        private static string HashPassword(string password)
        {
            byte[] salt = RandomNumberGenerator.GetBytes(16);
            var pbkdf2 = new Rfc2898DeriveBytes(password, salt, 100_000, HashAlgorithmName.SHA256);
            byte[] hash = pbkdf2.GetBytes(32);
            return Convert.ToBase64String(salt) + ":" + Convert.ToBase64String(hash);
        }

        private static bool VerifyPassword(string password, string storedHash)
        {
            var parts = storedHash.Split(':');
            if (parts.Length != 2)
            {
                return false;
            }

            try
            {
                var salt = Convert.FromBase64String(parts[0]);
                var hash = Convert.FromBase64String(parts[1]);
                var pbkdf2 = new Rfc2898DeriveBytes(password, salt, 100_000, HashAlgorithmName.SHA256);
                var computed = pbkdf2.GetBytes(32);
                return CryptographicOperations.FixedTimeEquals(hash, computed);
            }
            catch
            {
                return false;
            }
        }
    }
}
