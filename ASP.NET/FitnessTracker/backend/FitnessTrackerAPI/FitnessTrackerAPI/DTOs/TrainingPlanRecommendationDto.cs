namespace FitnessTrackerAPI.DTOs
{
    public class TrainingPlanRecommendationRequestDto
    {
        public string Goal { get; set; } = "general fitness";
        public int WeeklyDays { get; set; } = 3;
        public string ExperienceLevel { get; set; } = "beginner";
    }

    public class TrainingPlanRecommendationDto
    {
        public string Goal { get; set; } = string.Empty;
        public int WeeklyDays { get; set; }
        public string ExperienceLevel { get; set; } = string.Empty;
        public string Summary { get; set; } = string.Empty;
        public string Source { get; set; } = "fallback";
        public List<TrainingPlanDayDto> Days { get; set; } = new();
    }

    public class TrainingPlanDayDto
    {
        public string DayName { get; set; } = string.Empty;
        public string Focus { get; set; } = string.Empty;
        public string Notes { get; set; } = string.Empty;
        public List<string> Exercises { get; set; } = new();
    }
}
