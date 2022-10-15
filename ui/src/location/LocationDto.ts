import {getStateName, StateCode} from "../nav/State";
import {SelectedLocation} from "./SelectedLocation";

export type LocationDto = {
    id: number;
    state: StateCode;
    city?: string;
}

export const getLocationLabel = (location: SelectedLocation) => {
    if (!location) {
        return "United States";
    }
    return location.city ? `${location.state} / ${location.city}` : getStateName(location.state);
}