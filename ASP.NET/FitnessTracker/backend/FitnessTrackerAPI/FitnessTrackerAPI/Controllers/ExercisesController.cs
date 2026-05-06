using FitnessTrackerAPI.Data;
using FitnessTrackerAPI.DTOs;
using FitnessTrackerAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
namespace FitnessTrackerAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class ExercisesController : Controller
    {
        //public IActionResult Index()
        //{
        //    return View();
        //}
        private readonly AppDbContext _context;

        public ExercisesController(AppDbContext context)
        {
            _context = context;
        }

        // POST: api/exercises/{exerciseId}/sets
        // Adds a new set to an exercise
        [HttpPost("{exerciseId}/sets")]
        public async Task<IActionResult> AddSet([FromRoute] int exerciseId, [FromBody] SetRecord set)
        {
            var exercise = await _context.Exercises
                .Include(e => e.Sets)
                .FirstOrDefaultAsync(e => e.Id == exerciseId);

            if (exercise == null)
                return NotFound();

            exercise.Sets.Add(set);
            await _context.SaveChangesAsync();

            //return Ok(exercise); // 直接返回实体可能会导致循环引用问题，或者暴露不必要的数据

            // 创建一个 DTO 来返回干净的数据结构，避免循环引用和过多的细节
            var resultDto = new ExerciseDto
            {
                Id = exercise.Id,
                Name = exercise.Name,
                Sets = exercise.Sets.Select(s => new SetRecordDto
                {
                    Id = s.Id,
                    Weight = s.Weight,
                    Reps = s.Reps
                }).ToList()
            };

            return Ok(resultDto); // 现在返回的是干净的 DTO 对象

        }
    }
}
