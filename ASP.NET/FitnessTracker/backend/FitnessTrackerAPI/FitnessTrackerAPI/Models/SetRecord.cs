using System.Text.Json.Serialization;

namespace FitnessTrackerAPI.Models
{
    public class SetRecord
    {
        public int Id { get; set; }

        public double Weight { get; set; }
        public int Reps { get; set; }

        public int ExerciseId { get; set; }

        public Exercise? Exercise { get; set; }
    }
}
