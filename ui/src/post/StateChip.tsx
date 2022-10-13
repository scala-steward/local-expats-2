import {FC} from "react";
import {useSelectedState} from "../location/SelectedState";
import Link from "next/link";
import Chip from "@mui/material/Chip";
import {StateCode, stateLabels} from "../nav/State";

export const StateChip: FC<{ state: StateCode }> = ({state}) => {
    const {setSelectedState} = useSelectedState();
    return (
        <Link href="/">
            <Chip
                sx={{mx: 1}}
                clickable
                color="primary"
                label={stateLabels[state]}
                variant="outlined"
                size="small"
                onClick={() => setSelectedState(state)}
            />
        </Link>
    );
}