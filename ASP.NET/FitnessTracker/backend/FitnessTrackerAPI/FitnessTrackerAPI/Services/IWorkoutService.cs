using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using FitnessTrackerAPI.DTOs;

namespace FitnessTrackerAPI.Services
{
    public interface IWorkoutService
    {
        Task<List<WorkoutDto>> GetAllAsync(CancellationToken ct = default);
        Task<WorkoutDto?> GetByIdAsync(int id, CancellationToken ct = default);
        Task<WorkoutDto> CreateAsync(CreateWorkoutDto dto, CancellationToken ct = default);
        Task<bool> UpdateAsync(int id, UpdateWorkoutDto dto, CancellationToken ct = default);
        Task<bool> DeleteAsync(int id, CancellationToken ct = default);
    }
}
