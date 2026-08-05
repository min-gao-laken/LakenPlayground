/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $WorkoutDto = {
    properties: {
        id: {
            type: 'number',
            format: 'int32',
        },
        date: {
            type: 'string',
            format: 'date-time',
        },
        notes: {
            type: 'string',
            isNullable: true,
        },
        exercises: {
            type: 'array',
            contains: {
                type: 'ExerciseDto',
            },
            isNullable: true,
        },
    },
} as const;
