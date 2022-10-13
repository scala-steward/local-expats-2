import {createQueryParams} from "./Utils";

type Fetch = typeof fetch;

const jsonOnSuccess = (response: Response) => {
    if (response.ok) {
        return response.json();
    } else {
        throw response.statusText;
    }
};

export function get<ResultType>(
    input: (RequestInfo | URL),
    params: Record<string, string | number | undefined> = {},
    init?: RequestInit,
): Promise<ResultType> {
    return fetch(`${input}?${createQueryParams(params)}`, init)
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
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    }
    const allInit = {...defaultInit, ...init};
    return fetch(input, allInit)
        .then(jsonOnSuccess);
}