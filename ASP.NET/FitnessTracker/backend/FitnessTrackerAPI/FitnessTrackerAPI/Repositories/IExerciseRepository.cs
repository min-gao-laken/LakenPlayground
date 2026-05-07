using System.Threading;
using System.Threading.Tasks;
using FitnessTrackerAPI.Models;

namespace FitnessTrackerAPI.Repositories
{
    public interface IExerciseRepository
    {
        Task<Exercise> AddAsync(Exercise exercise, CancellationToken ct = default);
        Task<Exercise?> GetByIdAsync(int id, CancellationToken ct = default);
    }
}
