using System.ComponentModel.DataAnnotations;

namespace FitnessTrackerAPI.DTOs
{
    public class CreateExerciseDto
    {
        [Required(ErrorMessage = "The exercise name is required")]
        [StringLength(100, MinimumLength = 1, ErrorMessage = "The exercise name must be between 1 and 100 characters")]
        public string Name { get; set; }
    }
}
