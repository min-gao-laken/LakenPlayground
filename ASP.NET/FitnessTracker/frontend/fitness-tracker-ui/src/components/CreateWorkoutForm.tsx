import type { FormEvent } from 'react'

type CreateWorkoutFormProps = {
  newWorkoutDate: string
  newWorkoutNotes: string
  loading: boolean
  onDateChange: (value: string) => void
  onNotesChange: (value: string) => void
  onSubmit: (event: FormEvent<HTMLFormElement>) => void
}

export function CreateWorkoutForm({
  newWorkoutDate,
  newWorkoutNotes,
  loading,
  onDateChange,
  onNotesChange,
  onSubmit,
}: CreateWorkoutFormProps) {
  return (
    <form className="form-panel" onSubmit={onSubmit}>
      <h3>Create Workout</h3>
      <label>
        Workout Date
        <input
          type="datetime-local"
          value={newWorkoutDate}
          onChange={(event) => onDateChange(event.target.value)}
          required
        />
      </label>
      <label>
        Notes
        <textarea
          value={newWorkoutNotes}
          onChange={(event) => onNotesChange(event.target.value)}
          placeholder="Optional, record workout goals or status"
          maxLength={500}
        />
      </label>
      <button className="btn-primary" type="submit" disabled={loading}>
        Create Workout
      </button>
    </form>
  )
}
