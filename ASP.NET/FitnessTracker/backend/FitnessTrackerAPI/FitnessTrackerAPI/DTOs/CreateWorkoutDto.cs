using System;
using System.Collections.Generic;

namespace FitnessTrackerAPI.DTOs
{
    public class CreateWorkoutDto
    {
        public DateTime Date { get; set; } = DateTime.Now;
        public string? Notes { get; set; }
        public List<CreateExerciseDto>? Exercises { get; set; }
    }
}
