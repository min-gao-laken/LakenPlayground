namespace FitnessTrackerAPI.DTOs
{
    public class WorkoutHistoryItemDto
    {
        public int Id { get; set; }
        public DateTime Date { get; set; }
        public string? Notes { get; set; }
        public int ExerciseCount { get; set; }
        public int SetCount { get; set; }
        public string Summary { get; set; } = string.Empty;
    }
}
