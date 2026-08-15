using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace FitnessTrackerAPI.Controllers
{
    [ApiController]
    [Route("api/ai")]
    [Authorize]
    public class AIController : ControllerBase
    {
        private readonly ITrainingPlanRecommendationService _recommendationService;

        public AIController(ITrainingPlanRecommendationService recommendationService)
        {
            _recommendationService = recommendationService;
        }

        [HttpPost("recommend-plan")]
        [ProducesResponseType(typeof(TrainingPlanRecommendationDto), StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult<TrainingPlanRecommendationDto>> RecommendPlan([FromBody] TrainingPlanRecommendationRequestDto request, CancellationToken ct)
        {
            if (request == null)
            {
                return BadRequest(new { message = "Request body is required." });
            }

            var recommendation = await _recommendationService.BuildPlanAsync(request, ct);
            return Ok(recommendation);
        }
    }
}
