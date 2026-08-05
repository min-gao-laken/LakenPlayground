import { OpenAPI, WorkoutsService } from './api/generated'
import type { CreateWorkoutDto, ExerciseDto, WorkoutDto } from './api/generated'
import type { TrainingStats, WorkoutHistoryItem } from './types/training'

export type Exercise = ExerciseDto
export type Workout = WorkoutDto
export type CreateWorkoutPayload = CreateWorkoutDto
export type AuthResponse = {
  token: string
  username: string
}

export type MeResponse = {
  username: string
  userId: string
}

export function getStoredAuthToken(): string | null {
  return window.localStorage.getItem('fitness_tracker_token')
}

export function setStoredAuthToken(token: string | null): void {
  if (token) {
    window.localStorage.setItem('fitness_tracker_token', token)
    OpenAPI.TOKEN = token
  } else {
    window.localStorage.removeItem('fitness_tracker_token')
    OpenAPI.TOKEN = undefined
  }
}

export function initializeAuthFromStorage(): string | null {
  const token = getStoredAuthToken()
  if (token) {
    OpenAPI.TOKEN = token
  }
  return token
}

export async function loginUser(username: string, password: string): Promise<AuthResponse> {
  const response = await fetch('/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
  })

  const payload = await response.json().catch(() => null)
  if (!response.ok) {
    throw new Error(payload?.message || 'Login failed')
  }

  return payload as AuthResponse
}

export async function registerUser(username: string, password: string): Promise<AuthResponse> {
  const response = await fetch('/api/auth/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
  })

  const payload = await response.json().catch(() => null)
  if (!response.ok) {
    throw new Error(payload?.message || 'Registration failed')
  }

  return payload as AuthResponse
}

export async function fetchMe(token?: string): Promise<MeResponse> {
  const response = await fetch('/api/auth/me', {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token ?? getStoredAuthToken() ?? ''}`,
    },
  })

  const payload = await response.json().catch(() => null)
  if (!response.ok) {
    throw new Error(payload?.message || 'Failed to fetch user information')
  }

  return payload as MeResponse
}

export async function fetchWorkouts(): Promise<Workout[]> {
  return WorkoutsService.getApiWorkouts()
}

export async function createWorkout(payload: CreateWorkoutPayload): Promise<Workout> {
  return WorkoutsService.postApiWorkouts({ requestBody: payload })
}

export async function addExercise(workoutId: number, name: string): Promise<Exercise> {
  return WorkoutsService.postApiWorkoutsExercises({ workoutId, requestBody: { name } }) as Promise<Exercise>
}

export async function fetchTrainingStats(): Promise<TrainingStats> {
  const response = await fetch('/api/workouts/stats', {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${getStoredAuthToken() ?? ''}`,
    },
  })

  const payload = await response.json().catch(() => null)
  if (!response.ok) {
    throw new Error(payload?.message || 'Failed to fetch training stats')
  }

  return payload as TrainingStats
}

export async function fetchWorkoutHistory(): Promise<WorkoutHistoryItem[]> {
  const response = await fetch('/api/workouts/history', {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${getStoredAuthToken() ?? ''}`,
    },
  })

  const payload = await response.json().catch(() => null)
  if (!response.ok) {
    throw new Error(payload?.message || 'Failed to fetch workout history')
  }

  return payload as WorkoutHistoryItem[]
}
