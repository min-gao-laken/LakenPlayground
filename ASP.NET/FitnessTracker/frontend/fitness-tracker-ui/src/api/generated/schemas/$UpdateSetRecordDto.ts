/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $UpdateSetRecordDto = {
    properties: {
        weight: {
            type: 'number',
            isRequired: true,
            format: 'double',
            maximum: 999.9,
            minimum: 0.1,
        },
        reps: {
            type: 'number',
            isRequired: true,
            format: 'int32',
            maximum: 999,
            minimum: 1,
        },
    },
} as const;
