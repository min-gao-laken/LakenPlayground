/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $CreateWorkoutDto = {
    properties: {
        date: {
            type: 'string',
            isRequired: true,
            format: 'date-time',
        },
        notes: {
            type: 'string',
            isNullable: true,
            maxLength: 500,
        },
        exercises: {
            type: 'array',
            contains: {
                type: 'CreateExerciseDto',
            },
            isNullable: true,
        },
    },
} as const;
