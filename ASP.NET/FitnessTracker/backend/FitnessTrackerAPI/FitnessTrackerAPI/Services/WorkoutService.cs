using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Models;
using FitnessTrackerAPI.Repositories;

namespace FitnessTrackerAPI.Services
{
    public class WorkoutService : IWorkoutService
    {
        private readonly IWorkoutRepository _repo;

        public WorkoutService(IWorkoutRepository repo)
        {
            _repo = repo;
        }

        public async Task<List<WorkoutDto>> GetAllAsync(CancellationToken ct = default)
        {
            var workouts = await _repo.GetAllAsync(ct);
            return workouts.Select(w => MapToDto(w)).ToList();
        }

        public async Task<WorkoutDto?> GetByIdAsync(int id, CancellationToken ct = default)
        {
            var w = await _repo.GetByIdAsync(id, ct);
            return w == null ? null : MapToDto(w);
        }

        public async Task<WorkoutDto> CreateAsync(CreateWorkoutDto dto, CancellationToken ct = default)
        {
            var exercises = dto.Exercises?.Select(e => new Exercise
            {
                Name = e.Name,
                Sets = new List<SetRecord>()
            }).ToList() ?? new List<Exercise>();

            var entity = new Workout
            {
                Date = dto.Date,
                Notes = dto.Notes,
                Exercises = exercises
            };

            var saved = await _repo.AddAsync(entity, ct);
            return MapToDto(saved);
        }

        public async Task<bool> UpdateAsync(int id, UpdateWorkoutDto dto, CancellationToken ct = default)
        {
            var existing = await _repo.GetByIdAsync(id, ct);
            if (existing == null) return false;
            existing.Date = dto.Date;
            existing.Notes = dto.Notes;
            await _repo.UpdateAsync(existing, ct);
            return true;
        }

        public async Task<bool> DeleteAsync(int id, CancellationToken ct = default)
        {
            var existing = await _repo.GetByIdAsync(id, ct);
            if (existing == null) return false;
            await _repo.DeleteAsync(existing, ct);
            return true;
        }

        private static WorkoutDto MapToDto(Workout w)
        {
            return new WorkoutDto
            {
                Id = w.Id,
                Date = w.Date,
                Notes = w.Notes,
                Exercises = w.Exercises?.Select(e => new ExerciseDto
                {
                    Id = e.Id,
                    Name = e.Name,
                    Sets = e.Sets?.Select(s => new SetRecordDto
                    {
                        Id = s.Id,
                        Weight = s.Weight,
                        Reps = s.Reps
                    }).ToList()
                }).ToList()
            };
        }
    }
}
