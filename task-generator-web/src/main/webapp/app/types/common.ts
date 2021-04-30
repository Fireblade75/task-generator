
export interface ApiError {
    error: string,
    message: string
}

export function isApiError(object: any): object is ApiError {
    return object !== null
        && typeof object === 'object'
        && 'error' in object 
        && typeof object.error === 'string';
}

export interface RequestResponse {
    ok: boolean,
    msg: string
}