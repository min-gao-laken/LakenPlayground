using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Models;
using FitnessTrackerAPI.Repositories;

namespace FitnessTrackerAPI.Services
{
    public class SetRecordService : ISetRecordService
    {
        private readonly ISetRecordRepository _setRepo;
        private readonly IExerciseRepository _exerciseRepo;

        public SetRecordService(ISetRecordRepository setRepo, IExerciseRepository exerciseRepo)
        {
            _setRepo = setRepo;
            _exerciseRepo = exerciseRepo;
        }

        public async Task<SetRecordDto> AddSetToExerciseAsync(int exerciseId, SetRecord set, CancellationToken ct = default)
        {
            var exercise = await _exerciseRepo.GetByIdAsync(exerciseId, ct);
            if (exercise == null) throw new KeyNotFoundException("Exercise not found");

            set.ExerciseId = exerciseId;
            var saved = await _setRepo.AddAsync(set, ct);

            // Optionally add to exercise.Sets
            exercise.Sets ??= new List<SetRecord>();
            exercise.Sets.Add(saved);

            return new SetRecordDto { Id = saved.Id, Weight = saved.Weight, Reps = saved.Reps };
        }
    }
}
