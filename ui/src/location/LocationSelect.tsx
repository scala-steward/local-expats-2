import * as React from 'react';
import {FC} from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import {getLocationLabel, LocationDto} from "./LocationDto";
import {getStateName, isStateCode} from "../nav/State";
import {SelectedLocation, useSelectedLocation} from "./SelectedLocation";
import {FilterOptionsState} from "@mui/material";

type LocationSelectProps = {
    value: SelectedLocation;
    onChange: (selectedLocation: SelectedLocation) => void;
}

export const LocationSelect: FC<LocationSelectProps> = ({
    value,
    onChange
}) => {
    const {locations} = useSelectedLocation();
    return (
        <Autocomplete
            isOptionEqualToValue={(option, value) => option.id === value.id}
            getOptionLabel={getLocationLabel}
            options={locations}
            value={value}
            onChange={(_, location) => {
                onChange(location)
            }}
            groupBy={(location) => location.state}
            filterOptions={
                (locations: LocationDto[], state: FilterOptionsState<LocationDto>) => {
                    const input = state.inputValue.toUpperCase();
                    return locations.filter(
                        (location) =>
                            location.state.includes(input)
                            || state.getOptionLabel(location).toUpperCase().includes(input)
                    ).sort((l1, l2) => {
                        // Put the state code match first
                        if (!isStateCode(input)) {
                            return 0;
                        }
                        if (l1.state === l2.state) {
                            return 0;
                        }
                        return l1.state === input ? -1 : 0;
                    });
                }
            }
            renderInput={(params) => (
                <TextField
                    {...params}
                    label="United States"
                    InputProps={{
                        ...params.InputProps,
                    }}
                />
            )}
        />
    );
}