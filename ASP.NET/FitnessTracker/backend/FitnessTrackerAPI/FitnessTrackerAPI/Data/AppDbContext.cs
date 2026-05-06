using Microsoft.EntityFrameworkCore;
using FitnessTrackerAPI.Models;

namespace FitnessTrackerAPI.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options)
       : base(options) { }

        public DbSet<Workout> Workouts { get; set; }
        public DbSet<Exercise> Exercises { get; set; }
        public DbSet<SetRecord> SetRecords { get; set; }
    }
}
