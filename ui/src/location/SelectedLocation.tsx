import {createContext, FC, PropsWithChildren, useContext, useState} from "react";
import {LocationDto} from "./LocationDto";
import {get} from "../util/Fetch";
import {useQuery} from "@tanstack/react-query";

export type LocationId = number | undefined;
export type SelectedLocation = LocationDto | null;
type SetSelectedLocation = (selectedLocation: SelectedLocation) => void;
type SetSelectedLocationId = (selectedLocationId: LocationId) => void;

type UseSelectedLocation = {
    locations: readonly LocationDto[]
    selectedLocation: SelectedLocation;
    setSelectedLocation: SetSelectedLocation;
    setSelectedLocationId: SetSelectedLocationId;
    getLocation: (locationId: LocationId) => SelectedLocation;
}

const SelectedLocationContext = createContext<UseSelectedLocation | undefined>(undefined);

export const SelectedLocationProvider: FC<PropsWithChildren> = ({children}) => {
    const [selectedLocation, setSelectedLocation] = useState<SelectedLocation>(null);
    const fetchLocations = () => get<LocationDto[]>('/api/locations');
    const {data} = useQuery(['locations'], fetchLocations, {
        refetchOnWindowFocus: false,
        refetchOnMount: false,
        refetchOnReconnect: false,
        staleTime: Infinity,
    });
    const locations = data ?? [];
    const getLocation = (locationId: LocationId) => locations.find(({id}) => id === locationId) ?? null;

    const setSelectedLocationId = (locationId: LocationId) => {
        const location = locationId ? getLocation(locationId) : null;
        setSelectedLocation(location);
    }

    return (
        <SelectedLocationContext.Provider value={{locations, getLocation, selectedLocation, setSelectedLocation, setSelectedLocationId}}>
            {children}
        </SelectedLocationContext.Provider>
    );
}

export function useSelectedLocation(): UseSelectedLocation {
    const useSelectedLocationContext = useContext(SelectedLocationContext);
    if (useSelectedLocationContext === undefined) {
        throw new Error('useSelectedLocation must be used within a SelectedLocationProvider');
    }
    return useSelectedLocationContext;
}