using FitnessTrackerAPI.Data;
using Microsoft.AspNetCore.Mvc;

namespace FitnessTrackerAPI.Controllers
{
    [ApiController]
    [Route("api/test")]
    public class TestController : ControllerBase
    {
        private readonly AppDbContext _context;

        public TestController(AppDbContext context)
        {
            _context = context;
        }

        // API: api/test
        [HttpGet]
        public IActionResult Get()
        {
            return Ok("Database connected!");
        }

        // API: api/test/logerror
        [HttpGet("logerror")]
        public IActionResult TestLogError() {
            throw new Exception("Testing Middleware Log");
        }
    }
}
