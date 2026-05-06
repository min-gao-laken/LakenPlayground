namespace FitnessTrackerAPI.DTOs
{
    public class ExerciseDto
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public List<SetRecordDto> Sets { get; set; }
    }
}
