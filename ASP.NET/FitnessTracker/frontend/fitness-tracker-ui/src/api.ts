import { OpenAPI, WorkoutsService } from './api/generated'
import { ApiError } from './api/generated/core/ApiError'
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

type UnauthorizedHandler = () => void

let unauthorizedHandler: UnauthorizedHandler | null = null

export function setUnauthorizedHandler(handler: UnauthorizedHandler | null): void {
  unauthorizedHandler = handler
}

function notifyUnauthorized(): void {
  unauthorizedHandler?.()
}

function isUnauthorizedError(error: unknown): boolean {
  return error instanceof ApiError && error.status === 401
}

function getErrorMessage(payload: unknown, fallback: string): string {
  if (payload && typeof payload === 'object' && 'message' in payload) {
    const message = (payload as { message?: unknown }).message
    if (typeof message === 'string' && message.trim()) {
      return message
    }
  }

  return fallback
}

async function parseResponse<T>(response: Response, fallback: string): Promise<T> {
  const payload = await response.json().catch(() => null)

  if (!response.ok) {
    if (response.status === 401) {
      notifyUnauthorized()
    }

    throw new Error(getErrorMessage(payload, fallback))
  }

  return payload as T
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

  return await parseResponse<AuthResponse>(response, 'Login failed')
}

export async function registerUser(username: string, password: string): Promise<AuthResponse> {
  const response = await fetch('/api/auth/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
  })

  return await parseResponse<AuthResponse>(response, 'Registration failed')
}

export async function fetchMe(token?: string): Promise<MeResponse> {
  const response = await fetch('/api/auth/me', {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token ?? getStoredAuthToken() ?? ''}`,
    },
  })

  return await parseResponse<MeResponse>(response, 'Failed to fetch user information')
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

  return await parseResponse<TrainingStats>(response, 'Failed to fetch training stats')
}

export async function fetchWorkoutHistory(): Promise<WorkoutHistoryItem[]> {
  const response = await fetch('/api/workouts/history', {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${getStoredAuthToken() ?? ''}`,
    },
  })

  return await parseResponse<WorkoutHistoryItem[]>(response, 'Failed to fetch workout history')
}

export type TrainingPlanRecommendationRequest = {
  goal: string
  weeklyDays: number
  experienceLevel: string
}

export type TrainingPlanRecommendation = {
  goal: string
  weeklyDays: number
  experienceLevel: string
  summary: string
  source: 'gemini' | 'fallback'
  days: Array<{
    dayName: string
    focus: string
    notes: string
    exercises: string[]
  }>
}

export async function recommendTrainingPlan(request: TrainingPlanRecommendationRequest): Promise<TrainingPlanRecommendation> {
  const response = await fetch('/api/ai/recommend-plan', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${getStoredAuthToken() ?? ''}`,
    },
    body: JSON.stringify(request),
  })

  return await parseResponse<TrainingPlanRecommendation>(response, 'Failed to generate training plan')
}

export function handleApiError(error: unknown): void {
  if (isUnauthorizedError(error)) {
    notifyUnauthorized()
  }
}
