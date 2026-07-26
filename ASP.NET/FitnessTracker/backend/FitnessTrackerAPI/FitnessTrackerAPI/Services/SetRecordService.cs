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

        public async Task<SetRecordDto> AddSetToExerciseAsync(int exerciseId, CreateSetRecordDto dto, CancellationToken ct = default)
        {
            var exercise = await _exerciseRepo.GetByIdAsync(exerciseId, ct);
            if (exercise == null) throw new KeyNotFoundException("Exercise not found");

            var set = new SetRecord
            {
                ExerciseId = exerciseId,
                Weight = dto.Weight,
                Reps = dto.Reps
            };
            var saved = await _setRepo.AddAsync(set, ct);

            exercise.Sets ??= new List<SetRecord>();
            exercise.Sets.Add(saved);

            return MapToDto(saved);
        }

        public async Task<SetRecordDto?> GetByIdAsync(int id, CancellationToken ct = default)
        {
            var set = await _setRepo.GetByIdAsync(id, ct);
            if (set == null) return null;
            return MapToDto(set);
        }

        public async Task<bool> UpdateAsync(int id, UpdateSetRecordDto dto, CancellationToken ct = default)
        {
            var set = await _setRepo.GetByIdAsync(id, ct);
            if (set == null) return false;

            set.Weight = dto.Weight;
            set.Reps = dto.Reps;
            await _setRepo.UpdateAsync(set, ct);
            return true;
        }

        public async Task<bool> DeleteAsync(int id, CancellationToken ct = default)
        {
            var set = await _setRepo.GetByIdAsync(id, ct);
            if (set == null) return false;
            await _setRepo.DeleteAsync(set, ct);
            return true;
        }

        private static SetRecordDto MapToDto(SetRecord set)
        {
            return new SetRecordDto
            {
                Id = set.Id,
                Weight = set.Weight,
                Reps = set.Reps,
                ExerciseId = set.ExerciseId
            };
        }
    }
}
