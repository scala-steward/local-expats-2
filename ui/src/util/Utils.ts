export const createQueryParams = (params: Record<string, string | number | undefined>) => {
    return Object.entries(params)
        .filter(([_, value]) => value !== undefined)
        .map(([key, value]) => `${key}=${value}`)
        .join('&');
}