using System.Net.Http.Headers;
using System.Text;
using System.Text.Json;
using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Repositories;

namespace FitnessTrackerAPI.Services
{
    public class TrainingPlanRecommendationService : ITrainingPlanRecommendationService
    {
        private readonly IWorkoutRepository _workoutRepository;
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly IConfiguration _configuration;

        public TrainingPlanRecommendationService(IWorkoutRepository workoutRepository, IHttpClientFactory httpClientFactory, IConfiguration configuration)
        {
            _workoutRepository = workoutRepository;
            _httpClientFactory = httpClientFactory;
            _configuration = configuration;
        }

        public async Task<TrainingPlanRecommendationDto> BuildPlanAsync(TrainingPlanRecommendationRequestDto request, CancellationToken ct = default)
        {
            var goal = string.IsNullOrWhiteSpace(request.Goal) ? "general fitness" : request.Goal.Trim();
            var weeklyDays = request.WeeklyDays <= 0 ? 3 : Math.Clamp(request.WeeklyDays, 2, 6);
            var experienceLevel = string.IsNullOrWhiteSpace(request.ExperienceLevel) ? "beginner" : request.ExperienceLevel.Trim();

            var workouts = await _workoutRepository.GetAllAsync(ct);
            var recentWorkouts = workouts
                .Where(w => w.Date >= DateTime.UtcNow.AddDays(-30))
                .ToList();

            var apiKey = _configuration["Gemini:ApiKey"] ?? Environment.GetEnvironmentVariable("GEMINI_API_KEY");
            if (!string.IsNullOrWhiteSpace(apiKey))
            {
                var llmPlan = await TryGenerateFromGeminiAsync(goal, weeklyDays, experienceLevel, recentWorkouts, ct);
                if (llmPlan != null)
                {
                    Console.WriteLine("Training recommendation source: Gemini");
                    return llmPlan;
                }
            }

            Console.WriteLine("Training recommendation source: local fallback");

            var topExercises = GetTopExercises(recentWorkouts, 6);
            var exercisePool = topExercises.Count > 0 ? topExercises : GetFallbackExerciseList(goal);
            var days = BuildDays(exercisePool, goal, weeklyDays, experienceLevel);

            return new TrainingPlanRecommendationDto
            {
                Goal = goal,
                WeeklyDays = weeklyDays,
                ExperienceLevel = experienceLevel,
                Summary = CreateSummary(goal, weeklyDays, recentWorkouts.Count, exercisePool),
                Source = "fallback",
                Days = days
            };
        }

        private async Task<TrainingPlanRecommendationDto?> TryGenerateFromGeminiAsync(string goal, int weeklyDays, string experienceLevel, List<FitnessTrackerAPI.Models.Workout> recentWorkouts, CancellationToken ct)
        {
            try
            {
                var apiKey = _configuration["Gemini:ApiKey"] ?? Environment.GetEnvironmentVariable("GEMINI_API_KEY");
                if (string.IsNullOrWhiteSpace(apiKey))
                {
                    return null;
                }

                var preferredModel = _configuration["Gemini:Model"] ?? "gemini-2.5-flash";
                var modelCandidates = new List<string>
                {
                    preferredModel,
                    "gemini-2.5-flash",
                    "gemini-2.0-flash",
                    "gemini-2.0-flash-lite",
                    "gemini-3.6-flash"
                }
                .Distinct(StringComparer.OrdinalIgnoreCase)
                .ToList();

                var topExercises = GetTopExercises(recentWorkouts, 6);
                var workoutHistory = recentWorkouts
                    .OrderByDescending(w => w.Date)
                    .Take(10)
                    .Select(w => new
                    {
                        date = w.Date.ToString("yyyy-MM-dd"),
                        exercises = (w.Exercises ?? new List<FitnessTrackerAPI.Models.Exercise>())
                            .Where(e => !string.IsNullOrWhiteSpace(e.Name))
                            .Select(e => e.Name)
                            .ToList()
                    });

                var prompt = $"You are a professional fitness coach. Based on the user's recent workout history, generate a training plan in valid JSON only. " +
                    $"Goal: {goal}. WeeklyDays: {weeklyDays}. ExperienceLevel: {experienceLevel}. " +
                    $"Recent exercise history: {JsonSerializer.Serialize(workoutHistory)}. " +
                    $"Top exercises from history: {JsonSerializer.Serialize(topExercises.Count > 0 ? topExercises : GetFallbackExerciseList(goal))}. " +
                    "Return JSON with exactly this structure: {\"goal\": \"string\", \"weeklyDays\": number, \"experienceLevel\": \"string\", \"summary\": \"string\", \"days\": [{\"dayName\": \"string\", \"focus\": \"string\", \"notes\": \"string\", \"exercises\": [\"string\", \"string\"]}]}";

                var requestBody = new
                {
                    contents = new[]
                    {
                        new
                        {
                            parts = new[]
                            {
                                new { text = prompt }
                            }
                        }
                    }
                };

                using var httpClient = _httpClientFactory.CreateClient();

                foreach (var model in modelCandidates)
                {
                    var endpoint = $"https://generativelanguage.googleapis.com/v1beta/models/{model}:generateContent?key={apiKey}";
                    using var requestMessage = new HttpRequestMessage(HttpMethod.Post, endpoint);
                    requestMessage.Headers.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                    requestMessage.Content = new StringContent(JsonSerializer.Serialize(requestBody), Encoding.UTF8, "application/json");

                    using var response = await httpClient.SendAsync(requestMessage, HttpCompletionOption.ResponseHeadersRead, ct);
                    var payload = await response.Content.ReadAsStringAsync(ct);
                    if (!response.IsSuccessStatusCode)
                    {
                        Console.WriteLine($"Gemini model '{model}' request failed. Status: {(int)response.StatusCode} ({response.StatusCode}). Body: {payload}");
                        continue;
                    }

                    using var json = JsonDocument.Parse(payload);
                    var content = json.RootElement
                        .GetProperty("candidates")[0]
                        .GetProperty("content")
                        .GetProperty("parts")[0]
                        .GetProperty("text")
                        .GetString();

                    if (string.IsNullOrWhiteSpace(content))
                    {
                        return null;
                    }

                    var cleanedContent = content.Trim();
                    if (cleanedContent.StartsWith("```"))
                    {
                        cleanedContent = cleanedContent.Trim('`');
                        if (cleanedContent.StartsWith("json"))
                        {
                            cleanedContent = cleanedContent[4..].TrimStart();
                        }
                    }

                    using var planDoc = JsonDocument.Parse(cleanedContent);
                    var root = planDoc.RootElement;

                    var dayItems = new List<TrainingPlanDayDto>();
                    if (root.TryGetProperty("days", out var daysElement) && daysElement.ValueKind == JsonValueKind.Array)
                    {
                        foreach (var dayElement in daysElement.EnumerateArray())
                        {
                            var exercises = new List<string>();
                            if (dayElement.TryGetProperty("exercises", out var exercisesElement) && exercisesElement.ValueKind == JsonValueKind.Array)
                            {
                                foreach (var exerciseElement in exercisesElement.EnumerateArray())
                                {
                                    if (exerciseElement.ValueKind == JsonValueKind.String)
                                    {
                                        exercises.Add(exerciseElement.GetString() ?? string.Empty);
                                    }
                                }
                            }

                            dayItems.Add(new TrainingPlanDayDto
                            {
                                DayName = dayElement.TryGetProperty("dayName", out var dayNameElement) ? (dayNameElement.GetString() ?? "Day") : "Day",
                                Focus = dayElement.TryGetProperty("focus", out var focusElement) ? (focusElement.GetString() ?? "General") : "General",
                                Notes = dayElement.TryGetProperty("notes", out var notesElement) ? (notesElement.GetString() ?? string.Empty) : string.Empty,
                                Exercises = exercises
                            });
                        }
                    }

                    if (dayItems.Count == 0)
                    {
                        return null;
                    }

                    return new TrainingPlanRecommendationDto
                    {
                        Goal = root.TryGetProperty("goal", out var goalElement) ? (goalElement.GetString() ?? goal) : goal,
                        WeeklyDays = root.TryGetProperty("weeklyDays", out var weeklyDaysElement) && weeklyDaysElement.TryGetInt32(out var parsedWeeklyDays) ? parsedWeeklyDays : weeklyDays,
                        ExperienceLevel = root.TryGetProperty("experienceLevel", out var experienceElement) ? (experienceElement.GetString() ?? experienceLevel) : experienceLevel,
                        Summary = root.TryGetProperty("summary", out var summaryElement) ? (summaryElement.GetString() ?? string.Empty) : string.Empty,
                        Source = "gemini",
                        Days = dayItems
                    };
                }

                return null;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Gemini API exception: {ex}");
                return null;
            }
        }

        private static List<string> GetTopExercises(List<FitnessTrackerAPI.Models.Workout> workouts, int limit)
        {
            return workouts
                .SelectMany(w => w.Exercises ?? new List<FitnessTrackerAPI.Models.Exercise>())
                .Where(e => !string.IsNullOrWhiteSpace(e.Name))
                .GroupBy(e => e.Name, StringComparer.OrdinalIgnoreCase)
                .OrderByDescending(g => g.Count())
                .Select(g => g.Key)
                .Take(limit)
                .ToList();
        }

        private static List<string> GetFallbackExerciseList(string goal)
        {
            return goal.ToLowerInvariant() switch
            {
                "fat loss" => new List<string> { "Incline walk", "Kettlebell swing", "Push-up", "Goblet squat", "Row", "Plank" },
                "strength" => new List<string> { "Barbell squat", "Deadlift", "Bench press", "Overhead press", "Bent-over row", "Pull-up" },
                "muscle gain" => new List<string> { "Bench press", "Lat pulldown", "Goblet squat", "Dumbbell row", "Shoulder press", "Cable curl" },
                "endurance" => new List<string> { "Rowing", "Bike intervals", "Burpee", "Jump rope", "Mountain climber", "Light run" },
                _ => new List<string> { "Goblet squat", "Push-up", "Bent-over row", "Romanian deadlift", "Plank", "Bike intervals" }
            };
        }

        private static List<TrainingPlanDayDto> BuildDays(List<string> exercisePool, string goal, int weeklyDays, string experienceLevel)
        {
            var plan = new List<TrainingPlanDayDto>();
            var isFatLoss = goal.Equals("fat loss", StringComparison.OrdinalIgnoreCase);
            var isStrength = goal.Equals("strength", StringComparison.OrdinalIgnoreCase);
            var isMuscleGain = goal.Equals("muscle gain", StringComparison.OrdinalIgnoreCase);
            var isEndurance = goal.Equals("endurance", StringComparison.OrdinalIgnoreCase);

            var templates = new[]
            {
                new { DayName = "Day 1", Focus = "Upper body", Notes = "Start with compound work and keep the effort controlled." },
                new { DayName = "Day 2", Focus = "Lower body", Notes = "Prioritize full-range movement and stable tempo." },
                new { DayName = "Day 3", Focus = "Full body", Notes = "Combine strength with short conditioning blocks." },
                new { DayName = "Day 4", Focus = "Pull + core", Notes = "Focus on posture, core stability, and recovery." },
                new { DayName = "Day 5", Focus = "Conditioning", Notes = "Keep the session energetic but controlled." },
                new { DayName = "Day 6", Focus = "Recovery circuit", Notes = "Light movement and mobility work to support recovery." }
            };

            for (int i = 0; i < weeklyDays; i++)
            {
                var template = templates[i % templates.Length];
                var exerciseList = SelectExercisesForDay(exercisePool, i, isFatLoss, isStrength, isMuscleGain, isEndurance);

                plan.Add(new TrainingPlanDayDto
                {
                    DayName = template.DayName,
                    Focus = template.Focus,
                    Notes = GetDayNotes(template.Notes, goal, experienceLevel),
                    Exercises = exerciseList
                });
            }

            return plan;
        }

        private static List<string> SelectExercisesForDay(
            List<string> exercisePool,
            int index,
            bool isFatLoss,
            bool isStrength,
            bool isMuscleGain,
            bool isEndurance)
        {
            var fallback = new List<string>
            {
                "Goblet squat",
                "Bench press",
                "Bent-over row",
                "Push-up",
                "Plank",
                "Bike intervals"
            };

            var selected = new List<string>();
            var ordered = exercisePool.Count > 0 ? exercisePool : fallback;

            for (int i = 0; i < ordered.Count && selected.Count < 4; i++)
            {
                var candidate = ordered[(i + index) % ordered.Count];
                if (!selected.Contains(candidate, StringComparer.OrdinalIgnoreCase))
                {
                    selected.Add(candidate);
                }
            }

            if (selected.Count < 4)
            {
                foreach (var item in fallback)
                {
                    if (!selected.Contains(item, StringComparer.OrdinalIgnoreCase))
                    {
                        selected.Add(item);
                    }

                    if (selected.Count >= 4)
                    {
                        break;
                    }
                }
            }

            if (isFatLoss)
            {
                selected[0] = selected[0].Contains("walk", StringComparison.OrdinalIgnoreCase) ? selected[0] : "Incline walk";
                selected.Add("Conditioning finisher");
            }

            if (isStrength)
            {
                selected = new List<string> { "Barbell squat", "Bench press", "Deadlift", "Pull-up" };
            }

            if (isMuscleGain)
            {
                if (index % 2 == 0)
                {
                    selected = new List<string> { "Bench press", "Seated cable row", "Dumbbell shoulder press", "Cable curl" };
                }
                else
                {
                    selected = new List<string> { "Goblet squat", "Romanian deadlift", "Lat pulldown", "Plank" };
                }
            }

            if (isEndurance)
            {
                selected = new List<string> { "Rowing", "Bike intervals", "Jump rope", "Kettlebell swing" };
            }

            return selected.Take(4).ToList();
        }

        private static string GetDayNotes(string baseNote, string goal, string experienceLevel)
        {
            var experienceText = experienceLevel.ToLowerInvariant() switch
            {
                "advanced" => "Use heavier loads and keep rest periods around 90 seconds.",
                "intermediate" => "Stay in a controlled range and focus on clean tempo.",
                _ => "Keep intensity moderate and prioritize technique."
            };

            return $"{baseNote} {experienceText} Goal: {goal}.";
        }

        private static string CreateSummary(string goal, int weeklyDays, int recentWorkoutCount, List<string> exercisePool)
        {
            var focus = goal.ToLowerInvariant() switch
            {
                "fat loss" => "balanced conditioning and strength to help maintain muscle while improving calorie burn",
                "strength" => "compound lifts and progressive overload for better force production",
                "muscle gain" => "hypertrophy-focused sessions with solid volume and recovery",
                "endurance" => "cardio intervals and total-body work to raise work capacity",
                _ => "balanced full-body training to support consistency and general fitness"
            };

            var trend = recentWorkoutCount == 0
                ? "You have no recent workout history yet, so this is a clean-start plan."
                : $"Based on your recent {recentWorkoutCount}-workout trend, this plan leans on {string.Join(", ", exercisePool.Take(3))}.";

            return $"This {weeklyDays}-day plan is focused on {focus}. {trend}";
        }
    }
}
