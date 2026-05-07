using System.Threading;
using System.Threading.Tasks;
using FitnessTrackerAPI.Models;

namespace FitnessTrackerAPI.Repositories
{
    public interface ISetRecordRepository
    {
        Task<SetRecord> AddAsync(SetRecord set, CancellationToken ct = default);
    }
}
