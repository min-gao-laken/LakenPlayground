using System.Threading;
using System.Threading.Tasks;
using FitnessTrackerAPI.DTOs;

namespace FitnessTrackerAPI.Services
{
    public interface IExerciseService
    {
        Task<ExerciseDto?> GetByIdAsync(int id, CancellationToken ct = default);
        Task<ExerciseDto> AddToWorkoutAsync(int workoutId, CreateExerciseDto dto, CancellationToken ct = default);
        Task<bool> UpdateAsync(int id, UpdateExerciseDto dto, CancellationToken ct = default);
        Task<bool> DeleteAsync(int id, CancellationToken ct = default);
    }
}
