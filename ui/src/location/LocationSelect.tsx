import * as React from 'react';
import {FC} from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import {LocationDto} from "./LocationDto";
import {getStateName} from "../nav/State";
import {SelectedLocation, useSelectedLocation} from "./SelectedLocation";

type LocationSelectProps = {
    value: SelectedLocation;
    onChange: (selectedLocation: SelectedLocation) => void;
}

export const LocationSelect: FC<LocationSelectProps> = ({
    value,
    onChange
}) => {
    const {locations} = useSelectedLocation();
    const getLocationLabel = ({state, city}: LocationDto) =>
        city ? `${state} / ${city}` : getStateName(state)

    return (
        <Autocomplete
            isOptionEqualToValue={(option, value) => option.id === value.id}
            getOptionLabel={getLocationLabel}
            options={locations}
            value={value}
            onChange={(_, location) => {
                onChange(location)
            }}
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