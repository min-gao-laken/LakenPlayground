import type { Workout } from '../api'

type WorkoutListCardsProps = {
  workouts: Workout[]
  onSelectWorkout: (workoutId: number | '') => void
}

export function WorkoutListCards({ workouts, onSelectWorkout }: WorkoutListCardsProps) {
  return (
    <div className="workouts">
      {workouts.length === 0 ? (
        <p className="empty">No workout data yet, click refresh or submit the form to create a workout.</p>
      ) : (
        workouts.map((workout) => (
          <article key={workout.id ?? `workout-${workout.date}`} className="workout-card">
            <div className="workout-header">
              <h4>Workout #{workout.id ?? 'Unknown'}</h4>
              <span>{new Date(workout.date ?? '').toLocaleString()}</span>
            </div>
            <p className="notes">Notes: {workout.notes ?? 'None'}</p>
            <div className="exercise-list">
              <h5>Exercises</h5>
              {workout.exercises?.length ? (
                <ul>
                  {workout.exercises.map((exercise, index) => (
                    <li key={exercise.id ?? `exercise-${index}`}>{exercise.name ?? 'Unknown Exercise'}</li>
                  ))}
                </ul>
              ) : (
                <p className="empty">No exercises yet, use the "Add Exercise" form to create one.</p>
              )}
            </div>
            <button className="btn-secondary detail-toggle" type="button" onClick={() => onSelectWorkout(workout.id ?? '')}>
              View Details
            </button>
          </article>
        ))
      )}
    </div>
  )
}
