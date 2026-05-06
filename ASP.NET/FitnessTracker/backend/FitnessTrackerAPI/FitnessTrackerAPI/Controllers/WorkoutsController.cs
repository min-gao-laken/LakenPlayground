using FitnessTrackerAPI.Data;
using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace FitnessTrackerAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class WorkoutsController : ControllerBase
    {
        private readonly AppDbContext _context;

        public WorkoutsController(AppDbContext context)
        {
            _context = context;
        }

        // GET: api/workouts
        [HttpGet]
        public async Task<IActionResult> GetAll()
        {
            var workouts = await _context.Workouts.ToListAsync();
            return Ok(workouts);
        }

        // POST: api/workouts
        [HttpPost]
        public async Task<IActionResult> Create([FromBody] Workout workout)
        {
            _context.Workouts.Add(workout);
            await _context.SaveChangesAsync();
            return Ok(workout);
        }

        // Get by ID
        [HttpGet("{id}")]
        public async Task<IActionResult> GetById(int id)
        {
            var workout = await _context.Workouts.FindAsync(id);
            if (workout == null)
                return NotFound();
            return Ok(workout);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> Update(int id, Workout updated)
        {
            var workout = await _context.Workouts.FindAsync(id);
            if (workout == null)
                return NotFound();
            workout.Date = updated.Date;
            workout.Notes = updated.Notes;
            await _context.SaveChangesAsync();
            return Ok(workout);

        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            var workout = await _context.Workouts.FindAsync(id);
            if (workout == null)
                return NotFound();

            _context.Workouts.Remove(workout);
            await _context.SaveChangesAsync();
            return Ok();
        }

        // POST: api/workouts/{workoutId}/exercises
        // Adds an exercise to a specific workout
        [HttpPost("{workoutId}/exercises")]
        public async Task<IActionResult> AddExercise([FromRoute] int workoutId, [FromBody] CreateExerciseDto dto)
        {
            // 将 DTO 转换为实体再保存
            var exercise = new Exercise { Name = dto.Name };

            var workout = await _context.Workouts
                .Include(w => w.Exercises)
                .FirstOrDefaultAsync(w => w.Id == workoutId);

            if (workout == null)
                return NotFound();

            workout.Exercises.Add(exercise);

            await _context.SaveChangesAsync();

            return Ok(workout);
        }
    }
}
