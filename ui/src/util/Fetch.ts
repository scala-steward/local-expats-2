import {createQueryParams} from "./Utils";
import {getAuthHeader} from "../auth/Auth";

const jsonOnSuccess = (response: Response) => {
    if (response.ok) {
        return response.json();
    } else {
        throw response.json();
    }
};

export function get<ResultType>(
    input: (RequestInfo | URL),
    params: Record<string, string | number | undefined> = {},
    init?: RequestInit,
): Promise<ResultType> {
    const defaultInit = {
        headers: getAuthHeader()
    }
    const allInit = {...defaultInit, ...init};
    return fetch(`${input}?${createQueryParams(params)}`, allInit)
        .then(jsonOnSuccess);
}

export function post<ResultType>(
    input: (RequestInfo | URL),
    data: Record<string, unknown>,
    init?: RequestInit,
): Promise<ResultType> {
    const defaultInit = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...getAuthHeader()

        },
        body: JSON.stringify(data),
    }
    const allInit = {...defaultInit, ...init};
    return fetch(input, allInit)
        .then(jsonOnSuccess);
}