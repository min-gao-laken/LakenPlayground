using FitnessTrackerAPI.DTOs;

namespace FitnessTrackerAPI.Services
{
    public interface ITrainingPlanRecommendationService
    {
        Task<TrainingPlanRecommendationDto> BuildPlanAsync(TrainingPlanRecommendationRequestDto request, CancellationToken ct = default);
    }
}
