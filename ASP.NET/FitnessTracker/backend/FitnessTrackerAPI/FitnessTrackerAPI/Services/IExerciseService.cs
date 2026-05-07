using System.Threading;
using System.Threading.Tasks;
using FitnessTrackerAPI.DTOs;

namespace FitnessTrackerAPI.Services
{
    public interface IExerciseService
    {
        Task<ExerciseDto?> GetByIdAsync(int id, CancellationToken ct = default);
        Task<ExerciseDto> AddToWorkoutAsync(int workoutId, CreateExerciseDto dto, CancellationToken ct = default);
    }
}
