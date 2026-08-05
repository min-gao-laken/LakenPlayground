export type TrainingStats = {
  totalWorkouts: number
  totalExercises: number
  totalSets: number
  weeklyWorkouts: number
  monthlyWorkouts: number
  lastWorkoutDate: string | null
  lastWorkoutLabel: string | null
}

export type WorkoutHistoryItem = {
  id: number
  date: string
  notes: string | null
  exerciseCount: number
  setCount: number
  summary: string
}
