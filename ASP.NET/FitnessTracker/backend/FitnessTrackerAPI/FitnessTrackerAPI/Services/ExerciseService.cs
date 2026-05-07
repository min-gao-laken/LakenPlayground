using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Models;
using FitnessTrackerAPI.Repositories;

namespace FitnessTrackerAPI.Services
{
    public class ExerciseService : IExerciseService
    {
        private readonly IExerciseRepository _exerciseRepo;
        private readonly IWorkoutRepository _workoutRepo;

        public ExerciseService(IExerciseRepository exerciseRepo, IWorkoutRepository workoutRepo)
        {
            _exerciseRepo = exerciseRepo;
            _workoutRepo = workoutRepo;
        }

        public async Task<ExerciseDto?> GetByIdAsync(int id, CancellationToken ct = default)
        {
            var e = await _exerciseRepo.GetByIdAsync(id, ct);
            if (e == null) return null;
            return new ExerciseDto
            {
                Id = e.Id,
                Name = e.Name,
                Sets = e.Sets?.Select(s => new SetRecordDto { Id = s.Id, Reps = s.Reps, Weight = s.Weight }).ToList()
            };
        }

        public async Task<ExerciseDto> AddToWorkoutAsync(int workoutId, CreateExerciseDto dto, CancellationToken ct = default)
        {
            var workout = await _workoutRepo.GetByIdAsync(workoutId, ct);
            if (workout == null) throw new KeyNotFoundException("Workout not found");

            var exercise = new Exercise { Name = dto.Name, WorkoutId = workoutId };
            var saved = await _exerciseRepo.AddAsync(exercise, ct);

            // Optionally add to workout's navigation collection in-memory
            workout.Exercises ??= new List<Exercise>();
            workout.Exercises.Add(saved);

            return new ExerciseDto { Id = saved.Id, Name = saved.Name, Sets = new List<SetRecordDto>() };
        }
    }
}
