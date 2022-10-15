import {getStateName, StateCode} from "../nav/State";

export type LocationDto = {
    id: number;
    state?: StateCode;
    city?: string;
}

export const US = "United States";
export const getLocationLabel = ({city, state}: LocationDto): string =>
    city
        ? `${state} / ${city}`
        : state
            ? getStateName(state)
            : US;