using FitnessTrackerAPI.DTOs;
using Microsoft.AspNetCore.Mvc;
using FitnessTrackerAPI.Services;

namespace FitnessTrackerAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class WorkoutsController : ControllerBase
    {
        private readonly IWorkoutService _service;
        private readonly IExerciseService _exerciseService;

        public WorkoutsController(IWorkoutService service, IExerciseService exerciseService, ISetRecordService setRecordService)
        {
            _service = service;
            _exerciseService = exerciseService;
        }

        // GET: api/workouts
        [HttpGet]
        public async Task<ActionResult<IEnumerable<WorkoutDto>>> GetAll(CancellationToken ct)
        {
            var workouts = await _service.GetAllAsync(ct);
            return Ok(workouts);
        }

        // POST: api/workouts
        [HttpPost]
        public async Task<ActionResult<WorkoutDto>> Create([FromBody] CreateWorkoutDto dto, CancellationToken ct)
        {
            var created = await _service.CreateAsync(dto, ct);
            return CreatedAtAction(nameof(GetById), new { id = created.Id }, created);
        }

        // GET: api/workouts/{id}
        // Get by ID
        [HttpGet("{id}")]
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

        // POST: api/workouts/{workoutId}/exercises
        // Adds an exercise to a specific workout
        [HttpPost("{workoutId}/exercises")]
        public async Task<IActionResult> AddExercise([FromRoute] int workoutId, [FromBody] CreateExerciseDto dto, CancellationToken ct)
        {
            try
            {
                var created = await _exerciseService.AddToWorkoutAsync(workoutId, dto, ct);
                return CreatedAtAction("GetById", "Exercises", new { id = created.Id }, created);
            }
            catch (KeyNotFoundException)
            {
                return NotFound();
            }
        }
    }
}
