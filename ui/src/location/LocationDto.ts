import {getStateName, StateCode} from "../nav/State";
import {SelectedLocation} from "./SelectedLocation";

export type LocationDto = {
    id: number;
    state: StateCode;
    city?: string;
}

export const US = "United States";
export const getLocationLabel = (location: SelectedLocation) => {
    if (!location) {
        return US;
    }
    return location.city ? `${location.state} / ${location.city}` : getStateName(location.state);
}