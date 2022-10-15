import {StateCode} from "../nav/State";

export type LocationDto = Readonly<{
    id: number;
    state: StateCode;
    city?: string;
}>