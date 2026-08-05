import { useCallback, useEffect, useMemo, useState, type FormEvent } from 'react'
import type { Exercise, Workout } from '../api'
import { useApi } from './useApi'

type SetDraft = {
  weight: string
  reps: string
}

function toDateTimeLocalValue(value?: string) {
  if (!value) return ''
  // Strip timezone info (Z or +HH:mm) and take first 16 chars "YYYY-MM-DDTHH:mm"
  // Avoids local-timezone conversion that causes date drift on each save
  return value.replace(/Z$|[+-]\d{2}:\d{2}$/, '').slice(0, 16)
}

function toIsoDate(value: string) {
  // Send as-is without timezone conversion; backend stores as datetime2 (timezone-naive)
  return value.length === 16 ? value + ':00' : value
}

export function useWorkoutPage() {
  const {
    loading,
    error,
    setError,
    loadWorkouts,
    createWorkout,
    updateWorkout,
    deleteWorkout,
    addExercise,
    updateExercise,
    deleteExercise,
    addSet,
    updateSet,
    deleteSet,
  } = useApi()

  const [workouts, setWorkouts] = useState<Workout[]>([])
  const [newWorkoutDate, setNewWorkoutDate] = useState('')
  const [newWorkoutNotes, setNewWorkoutNotes] = useState('')
  const [selectedWorkoutId, setSelectedWorkoutId] = useState<number | ''>('')
  const [newExerciseName, setNewExerciseName] = useState('')
  const [workoutEditDate, setWorkoutEditDate] = useState('')
  const [workoutEditNotes, setWorkoutEditNotes] = useState('')
  const [exerciseNameDrafts, setExerciseNameDrafts] = useState<Record<number, string>>({})
  const [setDrafts, setSetDrafts] = useState<Record<number, SetDraft>>({})
  const [editingSet, setEditingSet] = useState<{ id: number | null; weight: string; reps: string }>({
    id: null,
    weight: '',
    reps: '',
  })
  const [success, setSuccess] = useState<string | null>(null)

  const selectedWorkout = useMemo(
    () => {
      if (selectedWorkoutId === '') return null
      return workouts.find((workout) => workout.id === selectedWorkoutId) ?? null
    },
    [selectedWorkoutId, workouts],
  )

  const loadData = useCallback(async () => {
    setError(null)

    try {
      const data = await loadWorkouts()
      setWorkouts(data)

      if (selectedWorkoutId !== '') {
        const stillExists = data.some((workout) => workout.id === selectedWorkoutId)
        if (!stillExists) {
          setSelectedWorkoutId('')
        }
      }
    } catch (err) {
      setError((err as Error).message)
    }
  }, [loadWorkouts, selectedWorkoutId, setError])

  useEffect(() => {
    void loadData()
  }, [loadData])

  useEffect(() => {
    if (!selectedWorkout) {
      setWorkoutEditDate('')
      setWorkoutEditNotes('')
      return
    }

    setWorkoutEditDate(toDateTimeLocalValue(selectedWorkout.date))
    setWorkoutEditNotes(selectedWorkout.notes ?? '')

    const nextDrafts: Record<number, string> = {}
    selectedWorkout.exercises?.forEach((exercise) => {
      if (exercise.id) {
        nextDrafts[exercise.id] = exercise.name ?? ''
      }
    })
    setExerciseNameDrafts(nextDrafts)
  }, [selectedWorkout])

  const handleCreateWorkout = useCallback(
    async (event: FormEvent<HTMLFormElement>) => {
      event.preventDefault()

      if (!newWorkoutDate.trim()) {
        setError('The workout date cannot be empty.')
        return
      }

      if (newWorkoutNotes.trim().length > 500) {
        setError('Notes cannot exceed 500 characters.')
        return
      }

      try {
        const payload = {
          date: toIsoDate(newWorkoutDate),
          notes: newWorkoutNotes.trim() ? newWorkoutNotes.trim() : null,
        }

        const created = await createWorkout(payload)
        setNewWorkoutDate('')
        setNewWorkoutNotes('')
        setSelectedWorkoutId(created.id ?? '')
        setSuccess('Workout created! Click "View Details" below or look at the modal to add exercises and sets.')
        setTimeout(() => setSuccess(null), 5000)
        await loadData()
      } catch (err) {
        setError((err as Error).message)
      }
    },
    [createWorkout, loadData, newWorkoutDate, newWorkoutNotes, setError],
  )

  const handleAddExercise = useCallback(
    async (event: FormEvent<HTMLFormElement>) => {
      event.preventDefault()

      if (!selectedWorkoutId) {
        setError('Please select a workout to add an exercise.')
        return
      }

      const trimmedName = newExerciseName.trim()
      if (!trimmedName) {
        setError('Please enter an exercise name.')
        return
      }

      if (trimmedName.length > 100) {
        setError('Exercise name cannot exceed 100 characters.')
        return
      }

      try {
        await addExercise(selectedWorkoutId, trimmedName)
        setNewExerciseName('')
        await loadData()
      } catch (err) {
        setError((err as Error).message)
      }
    },
    [addExercise, loadData, newExerciseName, selectedWorkoutId, setError],
  )

  const handleUpdateWorkout = useCallback(
    async (event: FormEvent<HTMLFormElement>) => {
      event.preventDefault()

      if (!selectedWorkoutId) {
        setError('Please select a workout first.')
        return
      }

      if (!workoutEditDate.trim()) {
        setError('The workout date cannot be empty.')
        return
      }

      if (workoutEditNotes.trim().length > 500) {
        setError('Notes cannot exceed 500 characters.')
        return
      }

      try {
        await updateWorkout(selectedWorkoutId, {
          date: toIsoDate(workoutEditDate),
          notes: workoutEditNotes.trim() ? workoutEditNotes.trim() : null,
        })
        await loadData()
      } catch (err) {
        setError((err as Error).message)
      }
    },
    [loadData, selectedWorkoutId, setError, updateWorkout, workoutEditDate, workoutEditNotes],
  )

  const handleDeleteWorkout = useCallback(async () => {
    if (!selectedWorkoutId) return

    const confirmed = window.confirm('Are you sure you want to delete this workout? This action cannot be undone.')
    if (!confirmed) return

    try {
      await deleteWorkout(selectedWorkoutId)
      setSelectedWorkoutId('')
      await loadData()
    } catch (err) {
      setError((err as Error).message)
    }
  }, [deleteWorkout, loadData, selectedWorkoutId, setError])

  const handleUpdateExercise = useCallback(
    async (exercise: Exercise) => {
      const exerciseId = exercise.id
      if (!exerciseId) return

      const trimmedName = (exerciseNameDrafts[exerciseId] ?? '').trim()
      if (!trimmedName) {
        setError('The exercise name cannot be empty.')
        return
      }

      try {
        await updateExercise(exerciseId, { name: trimmedName })
        await loadData()
      } catch (err) {
        setError((err as Error).message)
      }
    },
    [exerciseNameDrafts, loadData, setError, updateExercise],
  )

  const handleDeleteExercise = useCallback(
    async (exercise: Exercise) => {
      const exerciseId = exercise.id
      if (!exerciseId) return

      const confirmed = window.confirm('Are you sure you want to delete this exercise?')
      if (!confirmed) return

      try {
        await deleteExercise(exerciseId)
        await loadData()
      } catch (err) {
        setError((err as Error).message)
      }
    },
    [deleteExercise, loadData, setError],
  )

  const handleCreateSet = useCallback(
    async (exercise: Exercise) => {
      const exerciseId = exercise.id
      if (!exerciseId) return

      const draft = setDrafts[exerciseId] ?? { weight: '', reps: '' }
      const weight = Number(draft.weight)
      const reps = Number(draft.reps)

      if (!draft.weight || Number.isNaN(weight) || weight <= 0) {
        setError('Please enter a valid weight.')
        return
      }

      if (!draft.reps || Number.isNaN(reps) || reps <= 0) {
        setError('Please enter a valid number of reps.')
        return
      }

      try {
        await addSet(exerciseId, { weight, reps })
        setSetDrafts((prev) => ({ ...prev, [exerciseId]: { weight: '', reps: '' } }))
        await loadData()
      } catch (err) {
        setError((err as Error).message)
      }
    },
    [addSet, loadData, setDrafts, setError],
  )

  const handleStartEditSet = useCallback((setId: number, weight: number | undefined, reps: number | undefined) => {
    setEditingSet({ id: setId, weight: `${weight ?? ''}`, reps: `${reps ?? ''}` })
  }, [])

  const handleSaveSet = useCallback(async () => {
    if (!editingSet.id) return

    const weight = Number(editingSet.weight)
    const reps = Number(editingSet.reps)

    if (Number.isNaN(weight) || weight <= 0) {
      setError('Please enter a valid weight.')
      return
    }

    if (Number.isNaN(reps) || reps <= 0) {
      setError('Please enter a valid number of reps.')
      return
    }

    try {
      await updateSet(editingSet.id, { weight, reps })
      setEditingSet({ id: null, weight: '', reps: '' })
      await loadData()
    } catch (err) {
      setError((err as Error).message)
    }
  }, [editingSet.id, editingSet.reps, editingSet.weight, loadData, setError, updateSet])

  const handleDeleteSet = useCallback(async (setId: number) => {
    const confirmed = window.confirm('Are you sure you want to delete this set?')
    if (!confirmed) return

    try {
      await deleteSet(setId)
      await loadData()
    } catch (err) {
      setError((err as Error).message)
    }
  }, [deleteSet, loadData, setError])

  return {
    loading,
    error,
    success,
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
  } as const
}
