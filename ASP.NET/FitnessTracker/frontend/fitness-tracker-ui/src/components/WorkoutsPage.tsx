import type { Workout } from '../api'
import { AddExerciseForm } from './AddExerciseForm'
import { CreateWorkoutForm } from './CreateWorkoutForm'
import { WorkoutDetailPanel } from './WorkoutDetailPanel'
import { WorkoutListCards } from './WorkoutListCards'

type WorkoutExercise = NonNullable<Workout['exercises']>[number]

type WorkoutsPageProps = {
  workouts: Workout[]
  workoutsLoading: boolean
  selectedWorkoutId: number | ''
  newWorkoutDate: string
  newWorkoutNotes: string
  selectedWorkout: Workout | null
  newExerciseName: string
  workoutEditDate: string
  workoutEditNotes: string
  exerciseNameDrafts: Record<number, string>
  setDrafts: Record<number, { weight: string; reps: string }>
  editingSet: { id: number | null; weight: string; reps: string }
  success?: string | null
  onLoadWorkouts: () => Promise<void>
  onCreateWorkout: (event: React.FormEvent<HTMLFormElement>) => Promise<void>
  onDateChange: (date: string) => void
  onNotesChange: (notes: string) => void
  onSelectWorkout: (workoutId: number | '') => void
  onAddExercise: (event: React.FormEvent<HTMLFormElement>) => Promise<void>
  onExerciseNameChange: (name: string) => void
  onWorkoutDateChange: (date: string) => void
  onWorkoutNotesChange: (notes: string) => void
  onUpdateWorkout: (event: React.FormEvent<HTMLFormElement>) => Promise<void>
  onDeleteWorkout: () => Promise<void>
  onExerciseNameEdit: (exerciseId: number, value: string) => void
  onUpdateExercise: (exercise: WorkoutExercise) => Promise<void>
  onDeleteExercise: (exercise: WorkoutExercise) => Promise<void>
  onCreateSet: (exercise: WorkoutExercise) => Promise<void>
  onSetDraftChange: (exerciseId: number, field: string, value: string) => void
  onStartEditSet: (setId: number, weight?: number, reps?: number) => void
  onSaveSet: () => Promise<void>
  onCancelEditSet: () => void
  onDeleteSet: (setId: number) => Promise<void>
  onEditSetValueChange: (field: string, value: string) => void
}

export function WorkoutsPage({
  workouts,
  workoutsLoading,
  selectedWorkoutId,
  newWorkoutDate,
  newWorkoutNotes,
  selectedWorkout,
  newExerciseName,
  workoutEditDate,
  workoutEditNotes,
  exerciseNameDrafts,
  setDrafts,
  editingSet,
  success,
  onLoadWorkouts,
  onCreateWorkout,
  onDateChange,
  onNotesChange,
  onSelectWorkout,
  onAddExercise,
  onExerciseNameChange,
  onWorkoutDateChange,
  onWorkoutNotesChange,
  onUpdateWorkout,
  onDeleteWorkout,
  onExerciseNameEdit,
  onUpdateExercise,
  onDeleteExercise,
  onCreateSet,
  onSetDraftChange,
  onStartEditSet,
  onSaveSet,
  onCancelEditSet,
  onDeleteSet,
  onEditSetValueChange,
}: WorkoutsPageProps) {
  return (
    <div className="workouts-page">
      <div className="page-panel">
        <div className="page-panel__header">
          <div>
            <p className="page-panel__eyebrow">Workout management</p>
            <h2>Training sessions</h2>
          </div>
          <button className="btn-primary btn-primary--compact" onClick={() => void onLoadWorkouts()} disabled={workoutsLoading}>
            {workoutsLoading ? 'Loading...' : 'Refresh'}
          </button>
        </div>

        {success && (
          <div style={{
            padding: '1rem',
            borderRadius: '1rem',
            backgroundColor: 'rgba(34, 197, 94, 0.1)',
            border: '1px solid rgba(34, 197, 94, 0.3)',
            color: '#15803d',
            marginBottom: '1rem',
          }}>
            ✓ {success}
          </div>
        )}

        <div className="management-grid">
          <div id="create-workout-section" className="management-card">
            <CreateWorkoutForm
              newWorkoutDate={newWorkoutDate}
              newWorkoutNotes={newWorkoutNotes}
              loading={workoutsLoading}
              onDateChange={onDateChange}
              onNotesChange={onNotesChange}
              onSubmit={onCreateWorkout}
            />
          </div>

          <div id="add-exercise-section" className="management-card">
            <AddExerciseForm
              selectedWorkoutId={selectedWorkoutId}
              newExerciseName={newExerciseName}
              workoutsCount={workouts.length}
              loading={workoutsLoading}
              workouts={workouts}
              onWorkoutChange={onSelectWorkout}
              onNameChange={onExerciseNameChange}
              onSubmit={onAddExercise}
            />
          </div>
        </div>

        <WorkoutListCards workouts={workouts} onSelectWorkout={onSelectWorkout} />

        {selectedWorkout && (
          <>
            <div className="modal-overlay" onClick={() => onSelectWorkout('')} />
            <div className="modal-dialog">
              <div className="modal-header">
                <h3>Workout Details</h3>
                <button
                  className="modal-close"
                  type="button"
                  onClick={() => onSelectWorkout('')}
                  aria-label="Close"
                >
                  ✕
                </button>
              </div>
              <div className="modal-body">
                <WorkoutDetailPanel
                  workout={selectedWorkout}
                  loading={workoutsLoading}
                  workoutEditDate={workoutEditDate}
                  workoutEditNotes={workoutEditNotes}
                  newExerciseName={newExerciseName}
                  exerciseNameDrafts={exerciseNameDrafts}
                  setDrafts={setDrafts}
                  editingSet={editingSet}
                  onWorkoutDateChange={onWorkoutDateChange}
                  onWorkoutNotesChange={onWorkoutNotesChange}
                  onUpdateWorkout={onUpdateWorkout}
                  onDeleteWorkout={onDeleteWorkout}
                  onAddExercise={onAddExercise}
                  onExerciseNameChange={(exerciseId, value) => onExerciseNameEdit(exerciseId, value)}
                  onNewExerciseNameChange={onExerciseNameChange}
                  onUpdateExercise={onUpdateExercise}
                  onDeleteExercise={onDeleteExercise}
                  onCreateSet={onCreateSet}
                  onSetDraftChange={onSetDraftChange}
                  onStartEditSet={onStartEditSet}
                  onSaveSet={onSaveSet}
                  onCancelEditSet={onCancelEditSet}
                  onDeleteSet={onDeleteSet}
                  onEditSetValueChange={onEditSetValueChange}
                />
              </div>
            </div>
          </>
        )}
      </div>
    </div>
  )
}
