using System.Threading;
using System.Threading.Tasks;
using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Models;

namespace FitnessTrackerAPI.Services
{
    public interface ISetRecordService
    {
        Task<SetRecordDto> AddSetToExerciseAsync(int exerciseId, SetRecord set, CancellationToken ct = default);
    }
}
