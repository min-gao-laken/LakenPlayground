using Microsoft.AspNetCore.Mvc;
using FitnessTrackerAPI.Data;
using FitnessTrackerAPI.Models;
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
        public async Task<IActionResult> Create(Workout workout)
        {
            _context.Workouts.Add(workout);
            await _context.SaveChangesAsync();
            return Ok(workout);
        }
    }
}
