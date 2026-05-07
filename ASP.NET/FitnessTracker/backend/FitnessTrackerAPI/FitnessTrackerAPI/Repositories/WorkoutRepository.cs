using FitnessTrackerAPI.Data;
using FitnessTrackerAPI.Models;
using Microsoft.EntityFrameworkCore;

namespace FitnessTrackerAPI.Repositories
{
    public class WorkoutRepository : IWorkoutRepository
    {
        private readonly AppDbContext _context;

        public WorkoutRepository(AppDbContext context)
        {
            _context = context;
        }

        public Task<List<Workout>> GetAllAsync(CancellationToken ct = default)
        {
            return _context.Workouts
                .AsNoTracking()
                .ToListAsync(ct);
        }

        public Task<Workout?> GetByIdAsync(int id, CancellationToken ct = default)
        {
            return _context.Workouts
                .Include(w => w.Exercises)
                .ThenInclude(e => e.Sets)
                .AsNoTracking()
                .FirstOrDefaultAsync(w => w.Id == id, ct);
        }

        public async Task<Workout> AddAsync(Workout workout, CancellationToken ct = default)
        {
            _context.Workouts.Add(workout);
            await _context.SaveChangesAsync(ct);
            return workout;
        }

        public Task UpdateAsync(Workout workout, CancellationToken ct = default)
        {
            _context.Workouts.Update(workout);
            return _context.SaveChangesAsync(ct);
        }

        public Task DeleteAsync(Workout workout, CancellationToken ct = default)
        {
            _context.Workouts.Remove(workout);
            return _context.SaveChangesAsync(ct);
        }
    }
}
