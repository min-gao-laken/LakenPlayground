using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Services;
using Microsoft.AspNetCore.Mvc;

namespace FitnessTrackerAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class SetRecordsController : ControllerBase
    {
        private readonly ISetRecordService _service;

        public SetRecordsController(ISetRecordService service)
        {
            _service = service;
        }

        // GET: api/setrecords/{id}
        [HttpGet("{id}")]
        public async Task<ActionResult<SetRecordDto>> GetById(int id, CancellationToken ct)
        {
            var set = await _service.GetByIdAsync(id, ct);
            if (set == null)
                return NotFound();
            return Ok(set);
        }

        // PUT: api/setrecords/{id}
        [HttpPut("{id}")]
        public async Task<IActionResult> Update(int id, [FromBody] UpdateSetRecordDto dto, CancellationToken ct)
        {
            var ok = await _service.UpdateAsync(id, dto, ct);
            if (!ok) return NotFound();
            return NoContent();
        }

        // DELETE: api/setrecords/{id}
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id, CancellationToken ct)
        {
            var ok = await _service.DeleteAsync(id, ct);
            if (!ok) return NotFound();
            return NoContent();
        }
    }
}
