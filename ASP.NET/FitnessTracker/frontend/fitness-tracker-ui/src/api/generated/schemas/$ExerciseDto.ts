/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $ExerciseDto = {
    properties: {
        id: {
            type: 'number',
            format: 'int32',
        },
        name: {
            type: 'string',
            isNullable: true,
        },
        sets: {
            type: 'array',
            contains: {
                type: 'SetRecordDto',
            },
            isNullable: true,
        },
    },
} as const;
