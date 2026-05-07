using FitnessTrackerAPI.Data;
using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
namespace FitnessTrackerAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class ExercisesController : ControllerBase
    {
        //public IActionResult Index()
        //{
        //    return View();
        //}
        private readonly FitnessTrackerAPI.Services.IExerciseService _service;

        public ExercisesController(FitnessTrackerAPI.Services.IExerciseService service)
        {
            _service = service;
        }

        // GET: api/exercises/{id}
        // Get exercise by id
        [HttpGet("{id}")]
        public async Task<ActionResult<ExerciseDto>> GetById(int id, CancellationToken ct)
        {
            var dto = await _service.GetByIdAsync(id, ct);
            if (dto == null) return NotFound();
            return Ok(dto);
        }

        // POST: api/exercises/{exerciseId}/sets
        // Adds a new set to an exercise
        [HttpPost("{exerciseId}/sets")]
        public async Task<IActionResult> AddSet([FromRoute] int exerciseId, [FromBody] SetRecord set, CancellationToken ct)
        {
            try
            {
                var created = await HttpContext.RequestServices
                    .GetRequiredService<FitnessTrackerAPI.Services.ISetRecordService>()
                    .AddSetToExerciseAsync(exerciseId, set, ct);
                return CreatedAtAction(nameof(GetById), "Exercises", new { id = exerciseId }, created);
            }
            catch (KeyNotFoundException)
            {
                return NotFound();
            }
        }
    }
}
