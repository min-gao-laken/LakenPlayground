using FitnessTrackerAPI.Data;
using FitnessTrackerAPI.Models;

namespace FitnessTrackerAPI.Repositories
{
    public class SetRecordRepository : ISetRecordRepository
    {
        private readonly AppDbContext _context;

        public SetRecordRepository(AppDbContext context)
        {
            _context = context;
        }

        public async Task<SetRecord> AddAsync(SetRecord set, CancellationToken ct = default)
        {
            _context.SetRecords.Add(set);
            await _context.SaveChangesAsync(ct);
            return set;
        }
    }
}
