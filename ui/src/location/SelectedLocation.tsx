import {createContext, FC, PropsWithChildren, useContext, useState} from "react";
import {LocationDto} from "./LocationDto";
import {get} from "../util/Fetch";
import {useQuery} from "@tanstack/react-query";

export type SelectedLocation = LocationDto | null;
type SetSelectedLocation = (selectedLocation: SelectedLocation) => void;
type UseSelectedLocation = {
    locations: LocationDto[]
    selectedLocation: SelectedLocation;
    setSelectedLocation: SetSelectedLocation;
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
    return (
        <SelectedLocationContext.Provider value={{locations, selectedLocation, setSelectedLocation}}>
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