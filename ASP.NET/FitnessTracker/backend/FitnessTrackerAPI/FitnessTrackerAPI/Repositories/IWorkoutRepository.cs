using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using FitnessTrackerAPI.Models;

namespace FitnessTrackerAPI.Repositories
{
    public interface IWorkoutRepository
    {
        Task<List<Workout>> GetAllAsync(CancellationToken ct = default);
        Task<Workout?> GetByIdAsync(int id, CancellationToken ct = default);
        Task<Workout> AddAsync(Workout workout, CancellationToken ct = default);
        Task UpdateAsync(Workout workout, CancellationToken ct = default);
        Task DeleteAsync(Workout workout, CancellationToken ct = default);
    }
}
