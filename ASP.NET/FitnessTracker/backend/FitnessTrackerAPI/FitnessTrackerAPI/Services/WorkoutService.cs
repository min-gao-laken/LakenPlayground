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
                Date = dto.Date ?? DateTime.Now,
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
            existing.Date = dto.Date ?? DateTime.Now;
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

        public async Task<TrainingStatsDto> GetTrainingStatsAsync(CancellationToken ct = default)
        {
            var workouts = await _repo.GetAllAsync(ct);
            var allWorkouts = workouts.Where(w => w != null).ToList();
            var now = DateTime.UtcNow;
            var startOfWeek = now.Date.AddDays(-(int)now.DayOfWeek + (int)DayOfWeek.Monday);
            var startOfMonth = new DateTime(now.Year, now.Month, 1);

            var totalExercises = allWorkouts.Sum(w => w.Exercises?.Count ?? 0);
            var totalSets = allWorkouts.Sum(w => w.Exercises?.Sum(e => e.Sets?.Count ?? 0) ?? 0);
            var weeklyWorkouts = allWorkouts.Count(w => w.Date >= startOfWeek);
            var monthlyWorkouts = allWorkouts.Count(w => w.Date >= startOfMonth);
            var lastWorkout = allWorkouts.OrderByDescending(w => w.Date).FirstOrDefault();

            return new TrainingStatsDto
            {
                TotalWorkouts = allWorkouts.Count,
                TotalExercises = totalExercises,
                TotalSets = totalSets,
                WeeklyWorkouts = weeklyWorkouts,
                MonthlyWorkouts = monthlyWorkouts,
                LastWorkoutDate = lastWorkout?.Date,
                LastWorkoutLabel = lastWorkout == null ? "No workouts yet" : lastWorkout.Date.ToLocalTime().ToString("MMM dd, yyyy")
            };
        }

        public async Task<List<WorkoutHistoryItemDto>> GetHistoryAsync(CancellationToken ct = default)
        {
            var workouts = await _repo.GetAllAsync(ct);
            return workouts
                .OrderByDescending(w => w.Date)
                .Select(w => new WorkoutHistoryItemDto
                {
                    Id = w.Id,
                    Date = w.Date,
                    Notes = w.Notes,
                    ExerciseCount = w.Exercises?.Count ?? 0,
                    SetCount = w.Exercises?.Sum(e => e.Sets?.Count ?? 0) ?? 0,
                    Summary = BuildSummary(w)
                })
                .ToList();
        }

        private static string BuildSummary(Workout workout)
        {
            var exerciseNames = workout.Exercises?
                .Where(e => !string.IsNullOrWhiteSpace(e.Name))
                .Select(e => e.Name)
                .Take(3)
                .ToList() ?? new List<string>();

            if (exerciseNames.Count == 0)
            {
                return "No exercises recorded";
            }

            return string.Join(", ", exerciseNames);
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
                        Reps = s.Reps,
                        ExerciseId = s.ExerciseId
                    }).ToList()
                }).ToList()
            };
        }
    }
}
