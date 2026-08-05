/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateSetRecordDto } from '../models/CreateSetRecordDto';
import type { ExerciseDto } from '../models/ExerciseDto';
import type { UpdateExerciseDto } from '../models/UpdateExerciseDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ExercisesService {
    /**
     * @returns ExerciseDto OK
     * @throws ApiError
     */
    public static getApiExercises({
        id,
    }: {
        id: number,
    }): CancelablePromise<ExerciseDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/Exercises/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static putApiExercises({
        id,
        requestBody,
    }: {
        id: number,
        requestBody?: UpdateExerciseDto,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/Exercises/{id}',
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
    public static deleteApiExercises({
        id,
    }: {
        id: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/Exercises/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static postApiExercisesSets({
        exerciseId,
        requestBody,
    }: {
        exerciseId: number,
        requestBody?: CreateSetRecordDto,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/Exercises/{exerciseId}/sets',
            path: {
                'exerciseId': exerciseId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
