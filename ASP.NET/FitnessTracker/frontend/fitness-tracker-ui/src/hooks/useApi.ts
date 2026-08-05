import { useCallback, useState } from 'react'
import { ExercisesService, SetRecordsService, WorkoutsService } from '../api/generated'
import type { CreateSetRecordDto, CreateWorkoutDto, ExerciseDto, UpdateExerciseDto, UpdateSetRecordDto, UpdateWorkoutDto, WorkoutDto } from '../api/generated'

export function useApi() {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const run = useCallback(async <T>(action: () => Promise<T>): Promise<T> => {
    setLoading(true)
    setError(null)

    try {
      return await action()
    } catch (err) {
      setError((err as Error).message)
      throw err
    } finally {
      setLoading(false)
    }
  }, [])

  const loadWorkouts = useCallback(
    () => run(() => WorkoutsService.getApiWorkouts()),
    [run],
  )

  const createWorkout = useCallback(
    (payload: CreateWorkoutDto) => run(() => WorkoutsService.postApiWorkouts({ requestBody: payload })),
    [run],
  )

  const updateWorkout = useCallback(
    (id: number, payload: UpdateWorkoutDto) => run(() => WorkoutsService.putApiWorkouts({ id, requestBody: payload })),
    [run],
  )

  const deleteWorkout = useCallback(
    (id: number) => run(() => WorkoutsService.deleteApiWorkouts({ id })),
    [run],
  )

  const addExercise = useCallback(
    (workoutId: number, name: string) =>
      run(
        () =>
          WorkoutsService.postApiWorkoutsExercises({
            workoutId,
            requestBody: { name },
          }) as Promise<ExerciseDto>,
      ),
    [run],
  )

  const updateExercise = useCallback(
    (id: number, payload: UpdateExerciseDto) => run(() => ExercisesService.putApiExercises({ id, requestBody: payload })),
    [run],
  )

  const deleteExercise = useCallback(
    (id: number) => run(() => ExercisesService.deleteApiExercises({ id })),
    [run],
  )

  const addSet = useCallback(
    (exerciseId: number, payload: CreateSetRecordDto) => run(() => ExercisesService.postApiExercisesSets({ exerciseId, requestBody: payload })),
    [run],
  )

  const updateSet = useCallback(
    (id: number, payload: UpdateSetRecordDto) => run(() => SetRecordsService.putApiSetRecords({ id, requestBody: payload })),
    [run],
  )

  const deleteSet = useCallback(
    (id: number) => run(() => SetRecordsService.deleteApiSetRecords({ id })),
    [run],
  )

  return {
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
  } as const
}
