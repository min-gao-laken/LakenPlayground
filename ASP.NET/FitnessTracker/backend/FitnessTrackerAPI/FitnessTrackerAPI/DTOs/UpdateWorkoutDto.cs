using System;
using System.Collections.Generic;

namespace FitnessTrackerAPI.DTOs
{
    public class UpdateWorkoutDto
    {
        public DateTime Date { get; set; }
        public string? Notes { get; set; }
    }
}
