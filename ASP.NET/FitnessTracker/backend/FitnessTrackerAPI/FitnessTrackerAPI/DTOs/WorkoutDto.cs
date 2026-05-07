using System;
using System.Collections.Generic;

namespace FitnessTrackerAPI.DTOs
{
    public class WorkoutDto
    {
        public int Id { get; set; }
        public DateTime Date { get; set; }
        public string? Notes { get; set; }
        public List<ExerciseDto>? Exercises { get; set; }
    }
}
