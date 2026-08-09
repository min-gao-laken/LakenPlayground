using FitnessTrackerAPI.DTOs;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using FitnessTrackerAPI.Services;

namespace FitnessTrackerAPI.Controllers
{
    /// <summary>
    /// Workout management endpoints for creating, retrieving, updating and deleting training sessions
    /// </summary>
    [ApiController]
    [Route("api/[controller]")]
    [Authorize]
    public class WorkoutsController : ControllerBase
    {
        private readonly IWorkoutService _service;
        private readonly IExerciseService _exerciseService;

        public WorkoutsController(IWorkoutService service, IExerciseService exerciseService, ISetRecordService setRecordService)
        {
            _service = service;
            _exerciseService = exerciseService;
        }

        /// <summary>
        /// Get all workouts
        /// </summary>
        /// <param name="ct">Cancellation token</param>
        /// <returns>List of all workout records</returns>
        /// <response code="200">Returns the list of workouts</response>
        [HttpGet]
        [ProducesResponseType(typeof(IEnumerable<WorkoutDto>), StatusCodes.Status200OK)]
        public async Task<ActionResult<IEnumerable<WorkoutDto>>> GetAll(CancellationToken ct)
        {
            var workouts = await _service.GetAllAsync(ct);
            return Ok(workouts);
        }

        /// <summary>
        /// Create a new workout session
        /// </summary>
        /// <param name="dto">Workout creation data</param>
        /// <param name="ct">Cancellation token</param>
        /// <returns>The newly created workout</returns>
        /// <response code="201">Workout created successfully</response>
        /// <response code="400">Invalid workout data</response>
        [HttpPost]
        [ProducesResponseType(typeof(WorkoutDto), StatusCodes.Status201Created)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult<WorkoutDto>> Create([FromBody] CreateWorkoutDto dto, CancellationToken ct)
        {
            var created = await _service.CreateAsync(dto, ct);
            return CreatedAtAction(nameof(GetById), new { id = created.Id }, created);
        }

        /// <summary>
        /// Get a specific workout by ID
        /// </summary>
        /// <param name="id">Workout ID</param>
        /// <param name="ct">Cancellation token</param>
        /// <returns>The requested workout details</returns>
        /// <response code="200">Returns the workout</response>
        /// <response code="404">Workout not found</response>
        [HttpGet("{id}")]
        [ProducesResponseType(typeof(WorkoutDto), StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<WorkoutDto>> GetById(int id, CancellationToken ct)
        {
            var workout = await _service.GetByIdAsync(id, ct);
            if (workout == null)
                return NotFound();
            return Ok(workout);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> Update(int id, [FromBody] UpdateWorkoutDto dto, CancellationToken ct)
        {
            var ok = await _service.UpdateAsync(id, dto, ct);
            if (!ok) return NotFound();
            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id, CancellationToken ct)
        {
            var ok = await _service.DeleteAsync(id, ct);
            if (!ok) return NotFound();
            return NoContent();
        }

        /// <summary>
        /// Get training statistics summary
        /// </summary>
        /// <param name="ct">Cancellation token</param>
        /// <returns>Training statistics including workout count and other metrics</returns>
        /// <response code="200">Returns training statistics</response>
        [HttpGet("stats")]
        [ProducesResponseType(typeof(TrainingStatsDto), StatusCodes.Status200OK)]
        public async Task<ActionResult<TrainingStatsDto>> GetStats(CancellationToken ct)
        {
            var stats = await _service.GetTrainingStatsAsync(ct);
            return Ok(stats);
        }

        /// <summary>
        /// Get workout history
        /// </summary>
        /// <param name="ct">Cancellation token</param>
        /// <returns>List of workout history items</returns>
        /// <response code="200">Returns workout history</response>
        [HttpGet("history")]
        [ProducesResponseType(typeof(IEnumerable<WorkoutHistoryItemDto>), StatusCodes.Status200OK)]
        public async Task<ActionResult<IEnumerable<WorkoutHistoryItemDto>>> GetHistory(CancellationToken ct)
        {
            var history = await _service.GetHistoryAsync(ct);
            return Ok(history);
        }

        /// <summary>
        /// Add an exercise to a specific workout
        /// </summary>
        /// <param name="workoutId">Workout ID</param>
        /// <param name="dto">Exercise creation data</param>
        /// <param name="ct">Cancellation token</param>
        /// <returns>The created exercise</returns>
        /// <response code="201">Exercise added successfully</response>
        /// <response code="404">Workout not found</response>
        [HttpPost("{workoutId}/exercises")]
        [ProducesResponseType(typeof(ExerciseDto), StatusCodes.Status201Created)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<IActionResult> AddExercise([FromRoute] int workoutId, [FromBody] CreateExerciseDto dto, CancellationToken ct)
        {
            var created = await _exerciseService.AddToWorkoutAsync(workoutId, dto, ct);
            return CreatedAtAction(nameof(GetById), "Exercises", new { id = created.Id }, created);
        }
    }
}
