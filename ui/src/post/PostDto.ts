import {StateCode} from "../nav/State";

export interface PostDto {
    id: number;
    title: string;
    message?: string;
    state: StateCode;
    createdAt: string;
}