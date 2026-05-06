using System.Text.Json.Serialization;

namespace FitnessTrackerAPI.Models
{
    public class Exercise
    {
        public int Id { get; set; }
        public string Name { get; set; }

        public int WorkoutId { get; set; }

        public Workout? Workout { get; set; }

        public List<SetRecord>? Sets { get; set; }
    }
}
