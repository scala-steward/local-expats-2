import TextField from '@mui/material/TextField';
import {State, StateCode, stateCodes, stateLabels, states} from "./State";
import {Autocomplete, FilterOptionsState} from "@mui/material";
import Box from "@mui/material/Box";
import {FC} from "react";

interface StateSelectProps {
    onChange: (state: StateCode) => void;
    value?: StateCode;
    error?: boolean;
    label?: string;
}

export const StateSelect: FC<StateSelectProps> = ({
    onChange,
    value,
    error,
    label = "State",
}) => {

    return (
        <Autocomplete
            options={stateCodes}
            autoHighlight
            disableClearable
            value={value}
            getOptionLabel={(state: StateCode) => `${stateLabels[state]}`}
            renderOption={(props, state: StateCode) => (
                <Box component="li" {...props}>
                    {stateLabels[state]} ({state})
                </Box>
            )}
            onChange={(event, value) => onChange(value)}
            filterOptions={
                (options: StateCode[], state: FilterOptionsState<StateCode>) => {
                    const input = state.inputValue.toLocaleLowerCase();
                    return options.filter(
                        code => stateLabels[code].toLocaleLowerCase().includes(input)
                            || code.toLocaleLowerCase().includes(input)
                    );
                }
            }
            renderInput={(params) => (
                <TextField
                    {...params}
                    required
                    label={label}
                    variant="outlined"
                    inputProps={{
                        ...params.inputProps,
                    }}
                    error={error}
                />

            )}
        />
    );
}

