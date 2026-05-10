using FitnessTrackerAPI.Data;
using FitnessTrackerAPI.Models;
using Microsoft.EntityFrameworkCore;

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

        public Task<SetRecord?> GetByIdAsync(int id, CancellationToken ct = default)
        {
            return _context.SetRecords.FirstOrDefaultAsync(s => s.Id == id, ct);
        }

        public async Task UpdateAsync(SetRecord set, CancellationToken ct = default)
        {
            _context.SetRecords.Update(set);
            await _context.SaveChangesAsync(ct);
        }

        public async Task DeleteAsync(SetRecord set, CancellationToken ct = default)
        {
            _context.SetRecords.Remove(set);
            await _context.SaveChangesAsync(ct);
        }
    }
}
