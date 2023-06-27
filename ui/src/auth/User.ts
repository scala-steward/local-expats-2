export interface UserResponse {
    id: string;
    email: string;
    firstName: string;
    lastName: string;
}

export interface UserWithAuthTokenResponse {
    user: UserResponse;
    authToken: string;
}