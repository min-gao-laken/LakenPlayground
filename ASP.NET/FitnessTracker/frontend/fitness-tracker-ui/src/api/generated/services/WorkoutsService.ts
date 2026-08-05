/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateExerciseDto } from '../models/CreateExerciseDto';
import type { CreateWorkoutDto } from '../models/CreateWorkoutDto';
import type { UpdateWorkoutDto } from '../models/UpdateWorkoutDto';
import type { WorkoutDto } from '../models/WorkoutDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class WorkoutsService {
    /**
     * @returns WorkoutDto OK
     * @throws ApiError
     */
    public static getApiWorkouts(): CancelablePromise<Array<WorkoutDto>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/Workouts',
        });
    }
    /**
     * @returns WorkoutDto OK
     * @throws ApiError
     */
    public static postApiWorkouts({
        requestBody,
    }: {
        requestBody?: CreateWorkoutDto,
    }): CancelablePromise<WorkoutDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/Workouts',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns WorkoutDto OK
     * @throws ApiError
     */
    public static getApiWorkouts1({
        id,
    }: {
        id: number,
    }): CancelablePromise<WorkoutDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/Workouts/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static putApiWorkouts({
        id,
        requestBody,
    }: {
        id: number,
        requestBody?: UpdateWorkoutDto,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/Workouts/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static deleteApiWorkouts({
        id,
    }: {
        id: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/Workouts/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static postApiWorkoutsExercises({
        workoutId,
        requestBody,
    }: {
        workoutId: number,
        requestBody?: CreateExerciseDto,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/Workouts/{workoutId}/exercises',
            path: {
                'workoutId': workoutId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
