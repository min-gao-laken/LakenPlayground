using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace FitnessTrackerAPI.DTOs
{
    public class CreateWorkoutDto
    {
        [Required(ErrorMessage = "The date is required")]
        [DataType(DataType.DateTime)]
        public DateTime? Date { get; set; }

        [StringLength(500, ErrorMessage = "The notes must not exceed 500 characters")]
        public string? Notes { get; set; }

        public List<CreateExerciseDto>? Exercises { get; set; }
    }
}
