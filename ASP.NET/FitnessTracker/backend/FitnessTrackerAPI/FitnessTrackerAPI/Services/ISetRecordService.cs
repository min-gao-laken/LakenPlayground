using System.Threading;
using System.Threading.Tasks;
using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Models;

namespace FitnessTrackerAPI.Services
{
    public interface ISetRecordService
    {
        Task<SetRecordDto> AddSetToExerciseAsync(int exerciseId, CreateSetRecordDto dto, CancellationToken ct = default);
        Task<SetRecordDto?> GetByIdAsync(int id, CancellationToken ct = default);
        Task<bool> UpdateAsync(int id, UpdateSetRecordDto dto, CancellationToken ct = default);
        Task<bool> DeleteAsync(int id, CancellationToken ct = default);
    }
}
