using System.Text.Json.Serialization;

namespace FitnessTrackerAPI.Models
{
    public class Workout
    {
        public int Id { get; set; }
        public DateTime Date { get; set; } = DateTime.Now;
        public string? Notes { get; set; }

        public List<Exercise> Exercises { get; set; }

    }
}
