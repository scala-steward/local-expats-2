import {StateCode} from "../nav/State";

export type LocationDto = {
    id: number;
    state: StateCode;
    city?: string;
}