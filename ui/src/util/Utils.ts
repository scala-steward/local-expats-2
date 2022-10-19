export const createQueryParams = (params: Record<string, string | number | undefined>): string => {
    return Object.entries(params)
        .filter(([_, value]) => value !== undefined)
        .map(([key, value]) => `${key}=${value}`)
        .join('&');
}

export const toUiDateString = (dateTimeFromServer: string): string =>
    new Date(dateTimeFromServer).toLocaleDateString();

export const isHttps = () => (document.location.protocol === 'https:');
