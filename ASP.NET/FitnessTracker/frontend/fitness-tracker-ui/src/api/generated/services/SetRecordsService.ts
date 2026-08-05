/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SetRecordDto } from '../models/SetRecordDto';
import type { UpdateSetRecordDto } from '../models/UpdateSetRecordDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class SetRecordsService {
    /**
     * @returns SetRecordDto OK
     * @throws ApiError
     */
    public static getApiSetRecords({
        id,
    }: {
        id: number,
    }): CancelablePromise<SetRecordDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/SetRecords/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static putApiSetRecords({
        id,
        requestBody,
    }: {
        id: number,
        requestBody?: UpdateSetRecordDto,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/SetRecords/{id}',
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
    public static deleteApiSetRecords({
        id,
    }: {
        id: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/SetRecords/{id}',
            path: {
                'id': id,
            },
        });
    }
}
