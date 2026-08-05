import type { Exercise, Workout } from '../api'
import type { FormEvent } from 'react'

type WorkoutDetailPanelProps = {
  workout: Workout
  loading: boolean
  workoutEditDate: string
  workoutEditNotes: string
  newExerciseName: string
  exerciseNameDrafts: Record<number, string>
  setDrafts: Record<number, { weight: string; reps: string }>
  editingSet: { id: number | null; weight: string; reps: string }
  onWorkoutDateChange: (value: string) => void
  onWorkoutNotesChange: (value: string) => void
  onUpdateWorkout: (event: FormEvent<HTMLFormElement>) => void
  onDeleteWorkout: () => void
  onAddExercise: (event: FormEvent<HTMLFormElement>) => void
  onExerciseNameChange: (exerciseId: number, value: string) => void
  onNewExerciseNameChange: (name: string) => void
  onUpdateExercise: (exercise: Exercise) => void
  onDeleteExercise: (exercise: Exercise) => void
  onCreateSet: (exercise: Exercise) => void
  onSetDraftChange: (exerciseId: number, field: 'weight' | 'reps', value: string) => void
  onStartEditSet: (setId: number, weight: number | undefined, reps: number | undefined) => void
  onSaveSet: () => void
  onCancelEditSet: () => void
  onDeleteSet: (setId: number) => void
  onEditSetValueChange: (field: 'weight' | 'reps', value: string) => void
}

export function WorkoutDetailPanel({
  workout,
  loading,
  workoutEditDate,
  workoutEditNotes,
  newExerciseName,
  exerciseNameDrafts,
  setDrafts,
  editingSet,
  onWorkoutDateChange,
  onWorkoutNotesChange,
  onUpdateWorkout,
  onDeleteWorkout,
  onAddExercise,
  onExerciseNameChange,
  onNewExerciseNameChange,
  onUpdateExercise,
  onDeleteExercise,
  onCreateSet,
  onSetDraftChange,
  onStartEditSet,
  onSaveSet,
  onCancelEditSet,
  onDeleteSet,
  onEditSetValueChange,
}: WorkoutDetailPanelProps) {
  return (
    <div className="detail-panel">
      <div className="detail-header">
        <div>
          <p className="detail-label">Workout Details</p>
          <h3>Workout #{workout.id ?? 'Unknown'}</h3>
        </div>
        <div className="detail-actions">
          <button className="btn-secondary" type="button" onClick={onDeleteWorkout} disabled={loading}>
            Delete Workout
          </button>
        </div>
      </div>

      <form className="form-panel" onSubmit={onUpdateWorkout}>
        <h3>Edit Workout</h3>
        <label>
          Workout Date
          <input type="datetime-local" value={workoutEditDate} onChange={(event) => onWorkoutDateChange(event.target.value)} required />
        </label>
        <label>
          Notes
          <textarea value={workoutEditNotes} onChange={(event) => onWorkoutNotesChange(event.target.value)} maxLength={500} />
        </label>
        <button className="btn-primary" type="submit" disabled={loading}>
          Save Changes
        </button>
      </form>

      <div className="exercise-list detail-exercises">
        <h4>Exercises & Sets ({workout.exercises?.length || 0})</h4>

        {!workout.exercises?.length && (
          <div style={{ paddingBottom: '1.5rem', borderBottom: '1px solid rgba(148, 163, 184, 0.12)' }}>
            <p style={{ margin: '0 0 1rem', color: '#64748b', fontSize: '0.95rem' }}>No exercises in this workout yet. Add one to get started:</p>
            <form
              className="inline-form"
              onSubmit={onAddExercise}
              style={{ gap: '0.75rem' }}
            >
              <input
                type="text"
                value={newExerciseName}
                onChange={(event) => onNewExerciseNameChange(event.target.value)}
                placeholder="Exercise name (e.g., Bench Press)"
                maxLength={100}
                required
              />
              <button className="btn-secondary" type="submit" disabled={loading}>
                Add Exercise
              </button>
            </form>
          </div>
        )}

        {workout.exercises?.length ? (
          <div className="exercise-stack">
            {workout.exercises.map((exercise, index) => {
              const exerciseId = exercise.id ?? 0
              const draftValue = exerciseNameDrafts[exerciseId] ?? exercise.name ?? ''
              const setDraft = setDrafts[exerciseId] ?? { weight: '', reps: '' }

              return (
                <article key={exercise.id ?? `exercise-${index}`} className="exercise-card">
                  <div className="exercise-card-header">
                    <div style={{ flex: 1 }}>
                      <p style={{ margin: '0 0 0.5rem', fontSize: '0.82rem', fontWeight: 700, textTransform: 'uppercase', color: '#64748b' }}>
                        Exercise Name
                      </p>
                      <strong style={{ fontSize: '1.1rem', color: '#0f172a' }}>{exercise.name ?? 'Unknown Exercise'}</strong>
                    </div>
                    <button className="btn-danger" type="button" onClick={() => void onDeleteExercise(exercise)} disabled={loading}>
                      Delete Exercise
                    </button>
                  </div>

                  <div style={{ paddingTop: '0.75rem', borderTop: '1px solid rgba(148, 163, 184, 0.12)' }}>
                    <p style={{ margin: '0.75rem 0 0.5rem', fontSize: '0.82rem', fontWeight: 700, textTransform: 'uppercase', color: '#64748b' }}>
                      Edit Exercise Name
                    </p>
                    <form
                      className="inline-form"
                      onSubmit={(event) => {
                        event.preventDefault()
                        void onUpdateExercise(exercise)
                      }}
                    >
                      <input
                        type="text"
                        value={draftValue}
                        onChange={(event) => onExerciseNameChange(exerciseId, event.target.value)}
                        maxLength={100}
                        placeholder="Enter exercise name"
                      />
                      <button className="btn-secondary" type="submit" disabled={loading}>
                        Save Name
                      </button>
                    </form>
                  </div>

                  <div style={{ paddingTop: '1rem', borderTop: '1px solid rgba(148, 163, 184, 0.12)' }}>
                    <p style={{ margin: '0.75rem 0 0.5rem', fontSize: '0.82rem', fontWeight: 700, textTransform: 'uppercase', color: '#64748b' }}>
                      Add New Set
                    </p>
                    <div className="inline-form">
                      <input
                        type="number"
                        step="0.1"
                        placeholder="Weight (kg)"
                        value={setDraft.weight}
                        onChange={(event) => onSetDraftChange(exerciseId, 'weight', event.target.value)}
                      />
                      <input
                        type="number"
                        min="1"
                        placeholder="Reps"
                        value={setDraft.reps}
                        onChange={(event) => onSetDraftChange(exerciseId, 'reps', event.target.value)}
                      />
                      <button className="btn-secondary" type="button" onClick={() => void onCreateSet(exercise)} disabled={loading}>
                        Add Set
                      </button>
                    </div>
                  </div>

                  <div style={{ paddingTop: '1rem', borderTop: '1px solid rgba(148, 163, 184, 0.12)' }}>
                    <p style={{ margin: '0.75rem 0 0.75rem', fontSize: '0.82rem', fontWeight: 700, textTransform: 'uppercase', color: '#64748b' }}>
                      Sets ({exercise.sets?.length ?? 0})
                    </p>
                    <ul className="set-list">
                      {exercise.sets?.length ? (
                        exercise.sets.map((set) => (
                          <li key={set.id ?? `set-${exerciseId}-${index}`} className="set-item">
                            {editingSet.id === set.id ? (
                              <div className="inline-form" style={{ width: '100%' }}>
                                <input
                                  type="number"
                                  step="0.1"
                                  value={editingSet.weight}
                                  placeholder="Weight (kg)"
                                  onChange={(event) => onEditSetValueChange('weight', event.target.value)}
                                />
                                <input
                                  type="number"
                                  min="1"
                                  value={editingSet.reps}
                                  placeholder="Reps"
                                  onChange={(event) => onEditSetValueChange('reps', event.target.value)}
                                />
                                <button className="btn-secondary" type="button" onClick={() => void onSaveSet()} disabled={loading}>
                                  Save
                                </button>
                                <button className="btn-danger" type="button" onClick={onCancelEditSet}>
                                  Cancel
                                </button>
                              </div>
                            ) : (
                              <>
                                <span>Weight: {set.weight ?? '-'} kg · Reps: {set.reps ?? '-'}</span>
                                <div className="set-actions">
                                  <button className="btn-secondary" type="button" onClick={() => onStartEditSet(set.id ?? 0, set.weight, set.reps)}>
                                    Edit
                                  </button>
                                  <button className="btn-danger" type="button" onClick={() => void onDeleteSet(set.id ?? 0)}>
                                    Delete
                                  </button>
                                </div>
                              </>
                            )}
                          </li>
                        ))
                      ) : (
                        <li className="empty-set">No sets yet, add one!</li>
                      )}
                    </ul>
                  </div>
                </article>
              )
            })}
          </div>
        ) : null}
      </div>
    </div>
  )
}
