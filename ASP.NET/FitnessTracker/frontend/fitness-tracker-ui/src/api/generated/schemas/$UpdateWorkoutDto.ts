/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export const $UpdateWorkoutDto = {
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
    },
} as const;
