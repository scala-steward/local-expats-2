import {StateCode} from "../nav/State";

export interface PostDto {
    id: number;
    title: string;
    message: string;
    targetState: StateCode;
    createdAt: string;
}