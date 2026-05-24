using System.ComponentModel.DataAnnotations;

namespace FitnessTrackerAPI.DTOs
{
    public class UpdateSetRecordDto
    {
        [Required(ErrorMessage = "The weight is required")]
        [Range(0.1, 999.9, ErrorMessage = "The weight must be between 0.1 and 999.9")]
        public double Weight { get; set; }

        [Required(ErrorMessage = "The number of repetitions is required")]
        [Range(1, 999, ErrorMessage = "The number of repetitions must be between 1 and 999")]
        public int Reps { get; set; }
    }
}
