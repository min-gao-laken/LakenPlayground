using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Services;
using Microsoft.AspNetCore.Mvc;

namespace FitnessTrackerAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class ExercisesController : ControllerBase
    {
        private readonly IExerciseService _service;
        private readonly ISetRecordService _setRecordService;

        public ExercisesController(IExerciseService service, ISetRecordService setRecordService)
        {
            _service = service;
            _setRecordService = setRecordService;
        }

        // GET: api/exercises/{id}
        [HttpGet("{id}")]
        public async Task<ActionResult<ExerciseDto>> GetById(int id, CancellationToken ct)
        {
            var dto = await _service.GetByIdAsync(id, ct);
            if (dto == null) return NotFound();
            return Ok(dto);
        }

        // PUT: api/exercises/{id}
        [HttpPut("{id}")]
        public async Task<IActionResult> Update(int id, [FromBody] UpdateExerciseDto dto, CancellationToken ct)
        {
            var ok = await _service.UpdateAsync(id, dto, ct);
            if (!ok) return NotFound();
            return NoContent();
        }

        // DELETE: api/exercises/{id}
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id, CancellationToken ct)
        {
            var ok = await _service.DeleteAsync(id, ct);
            if (!ok) return NotFound();
            return NoContent();
        }

        // POST: api/exercises/{exerciseId}/sets
        [HttpPost("{exerciseId}/sets")]
        public async Task<IActionResult> AddSet([FromRoute] int exerciseId, [FromBody] CreateSetRecordDto dto, CancellationToken ct)
        {
            var created = await _setRecordService.AddSetToExerciseAsync(exerciseId, dto, ct);
            return CreatedAtAction("GetById", "SetRecords", new { id = created.Id }, created);
        }
    }
}
