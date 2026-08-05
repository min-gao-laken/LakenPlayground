namespace FitnessTrackerAPI.DTOs
{
    public class TrainingStatsDto
    {
        public int TotalWorkouts { get; set; }
        public int TotalExercises { get; set; }
        public int TotalSets { get; set; }
        public int WeeklyWorkouts { get; set; }
        public int MonthlyWorkouts { get; set; }
        public DateTime? LastWorkoutDate { get; set; }
        public string? LastWorkoutLabel { get; set; }
    }
}
