import { CreateWorkoutForm } from './components/CreateWorkoutForm'
import { AddExerciseForm } from './components/AddExerciseForm'
import { WorkoutDetailPanel } from './components/WorkoutDetailPanel'
import { WorkoutListCards } from './components/WorkoutListCards'
import { useWorkoutPage } from './hooks/useWorkoutPage'

export default function WorkoutList() {
  const {
    loading,
    error,
    workouts,
    newWorkoutDate,
    newWorkoutNotes,
    selectedWorkoutId,
    newExerciseName,
    workoutEditDate,
    workoutEditNotes,
    exerciseNameDrafts,
    setDrafts,
    editingSet,
    selectedWorkout,
    setNewWorkoutDate,
    setNewWorkoutNotes,
    setSelectedWorkoutId,
    setNewExerciseName,
    setWorkoutEditDate,
    setWorkoutEditNotes,
    setExerciseNameDrafts,
    setSetDrafts,
    setEditingSet,
    handleCreateWorkout,
    handleAddExercise,
    handleUpdateWorkout,
    handleDeleteWorkout,
    handleUpdateExercise,
    handleDeleteExercise,
    handleCreateSet,
    handleStartEditSet,
    handleSaveSet,
    handleDeleteSet,
    loadData,
  } = useWorkoutPage()

  const activeWorkoutLabel = selectedWorkout?.date ?? 'No workout selected'

  return (
    <section className="workout-list">
      <div className="page-header page-header--panel">
        <div className="page-header__top">
          <div>
            <p className="page-header__eyebrow">Workout Panel</p>
            <h2>Manage your fitness board</h2>
            <p>Create workouts, add exercises, and keep each training session tidy and actionable.</p>
          </div>
          <button className="btn-primary btn-primary--compact" onClick={() => void loadData()} disabled={loading}>
            {loading ? 'Loading...' : 'Refresh List'}
          </button>
        </div>

        <div className="page-header__stats">
          <div className="page-header__stat">
            <span>Workouts</span>
            <strong>{workouts.length}</strong>
          </div>
          <div className="page-header__stat">
            <span>Selected</span>
            <strong>{activeWorkoutLabel}</strong>
          </div>
          <div className="page-header__stat">
            <span>Exercises</span>
            <strong>{selectedWorkout?.exercises?.length ?? 0}</strong>
          </div>
        </div>
      </div>

      <div className="forms-grid">
        <CreateWorkoutForm
          newWorkoutDate={newWorkoutDate}
          newWorkoutNotes={newWorkoutNotes}
          loading={loading}
          onDateChange={setNewWorkoutDate}
          onNotesChange={setNewWorkoutNotes}
          onSubmit={handleCreateWorkout}
        />

        <AddExerciseForm
          selectedWorkoutId={selectedWorkoutId}
          newExerciseName={newExerciseName}
          workoutsCount={workouts.length}
          loading={loading}
          workouts={workouts}
          onWorkoutChange={setSelectedWorkoutId}
          onNameChange={setNewExerciseName}
          onSubmit={handleAddExercise}
        />
      </div>

      {error && <p className="error">Error: {error}</p>}

      {selectedWorkout ? (
        <WorkoutDetailPanel
          workout={selectedWorkout}
          loading={loading}
          workoutEditDate={workoutEditDate}
          workoutEditNotes={workoutEditNotes}
          exerciseNameDrafts={exerciseNameDrafts}
          setDrafts={setDrafts}
          editingSet={editingSet}
          onWorkoutDateChange={setWorkoutEditDate}
          onWorkoutNotesChange={setWorkoutEditNotes}
          onUpdateWorkout={handleUpdateWorkout}
          onDeleteWorkout={handleDeleteWorkout}
          onExerciseNameChange={(exerciseId, value) =>
            setExerciseNameDrafts((prev) => ({ ...prev, [exerciseId]: value }))
          }
          onUpdateExercise={handleUpdateExercise}
          onDeleteExercise={handleDeleteExercise}
          onCreateSet={handleCreateSet}
          onSetDraftChange={(exerciseId, field, value) =>
            setSetDrafts((prev) => ({
              ...prev,
              [exerciseId]: {
                weight: field === 'weight' ? value : prev[exerciseId]?.weight ?? '',
                reps: field === 'reps' ? value : prev[exerciseId]?.reps ?? '',
              },
            }))
          }
          onStartEditSet={handleStartEditSet}
          onSaveSet={handleSaveSet}
          onCancelEditSet={() => setEditingSet({ id: null, weight: '', reps: '' })}
          onDeleteSet={handleDeleteSet}
          onEditSetValueChange={(field, value) =>
            setEditingSet((prev) => ({ ...prev, [field]: value }))
          }
        />
      ) : (
        <div className="empty-state">Select a workout to view details and edit it here.</div>
      )}

      <WorkoutListCards workouts={workouts} onSelectWorkout={setSelectedWorkoutId} />
    </section>
  )
}
