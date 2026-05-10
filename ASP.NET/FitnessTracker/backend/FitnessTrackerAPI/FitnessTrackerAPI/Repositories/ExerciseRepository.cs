using FitnessTrackerAPI.Data;
using FitnessTrackerAPI.Models;
using Microsoft.EntityFrameworkCore;

namespace FitnessTrackerAPI.Repositories
{
    public class ExerciseRepository : IExerciseRepository
    {
        private readonly AppDbContext _context;

        public ExerciseRepository(AppDbContext context)
        {
            _context = context;
        }

        public async Task<Exercise> AddAsync(Exercise exercise, CancellationToken ct = default)
        {
            _context.Exercises.Add(exercise);
            await _context.SaveChangesAsync(ct);
            return exercise;
        }

        public Task<Exercise?> GetByIdAsync(int id, CancellationToken ct = default)
        {
            return _context.Exercises
                .Include(e => e.Sets)
                .FirstOrDefaultAsync(e => e.Id == id, ct);
        }

        public async Task UpdateAsync(Exercise exercise, CancellationToken ct = default)
        {
            _context.Exercises.Update(exercise);
            await _context.SaveChangesAsync(ct);
        }

        public async Task DeleteAsync(Exercise exercise, CancellationToken ct = default)
        {
            _context.Exercises.Remove(exercise);
            await _context.SaveChangesAsync(ct);
        }
    }
}
