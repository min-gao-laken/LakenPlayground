import type { FormEvent } from 'react'

type AddExerciseFormProps = {
  selectedWorkoutId: number | ''
  newExerciseName: string
  workoutsCount: number
  loading: boolean
  workouts: Array<{ id?: number; date?: string }>
  onWorkoutChange: (value: number | '') => void
  onNameChange: (value: string) => void
  onSubmit: (event: FormEvent<HTMLFormElement>) => void
}

export function AddExerciseForm({
  selectedWorkoutId,
  newExerciseName,
  workoutsCount,
  loading,
  workouts,
  onWorkoutChange,
  onNameChange,
  onSubmit,
}: AddExerciseFormProps) {
  return (
    <form className="form-panel" onSubmit={onSubmit}>
      <h3>Add Exercise</h3>
      <label>
        Select Workout
        <select value={selectedWorkoutId} onChange={(event) => onWorkoutChange(Number(event.target.value) || '')}>
          <option value="">Please select a workout</option>
          {workouts.map((workout) => (
            <option key={workout.id ?? `workout-${workout.date}`} value={workout.id}>
              #{workout.id ?? '未知'} - {new Date(workout.date ?? '').toLocaleString()}
            </option>
          ))}
        </select>
      </label>
      <label>
        Exercise Name
        <input
          type="text"
          value={newExerciseName}
          onChange={(event) => onNameChange(event.target.value)}
          placeholder="e.g., Squat"
          maxLength={100}
        />
      </label>
      <button className="btn-primary" type="submit" disabled={loading || !workoutsCount}>
        Add Exercise
      </button>
    </form>
  )
}
