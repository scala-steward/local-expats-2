import * as React from 'react';
import {FC} from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import {getLocationLabel, LocationDto, US} from "./LocationDto";
import {isStateCode} from "../nav/State";
import {useSelectedLocation} from "./SelectedLocation";
import {FilterOptionsState, Popper} from "@mui/material";
import {useIsSmallScreen} from "../util/useUtils";

type LocationSelectProps = {
    label?: string;
    error?: boolean
    value?: LocationDto;
    onChange: (selectedLocation: LocationDto) => void;
}

export const LocationSelect: FC<LocationSelectProps> = ({
    label,
    error,
    value,
    onChange
}) => {
    const {locations} = useSelectedLocation();
    const isSmallScreen = useIsSmallScreen();
    return (
        <Autocomplete
            PopperComponent={(props) =>
                <Popper
                    {...props}
                    {...(isSmallScreen && {style: {width: '100%'}})}
                />
            }
            isOptionEqualToValue={(option, value) => option.id === value.id}
            getOptionLabel={getLocationLabel}
            options={locations}
            disableClearable={!value || value === US}
            value={value}
            onChange={(_, location) => {
                onChange(location ?? US)
            }}
            groupBy={(location) => location.state || ''}
            filterOptions={
                (locations: LocationDto[], state: FilterOptionsState<LocationDto>) => {
                    const input = state.inputValue.toUpperCase();
                    return locations.filter(
                        (location) =>
                            (input === 'US' && !location.state)
                            || location.state?.includes(input)
                            || state.getOptionLabel(location).toUpperCase().includes(input)
                    ).sort((l1, l2) => {
                        if (!l1.state) {
                            return -1;
                        }
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
                    required
                    label={label}
                    {...params}
                    InputProps={{
                        ...params.InputProps,
                    }}
                    error={error}
                />
            )}
        />
    );
}