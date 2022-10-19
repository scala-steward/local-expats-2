import Chip from "@mui/material/Chip";
import {FC} from "react";
import {toUiDateString} from "../util/Utils";

export const DateChip: FC<{ date: string }> = ({date}) =>
    <Chip
        sx={{mx: 1 / 2}}
        variant="filled"
        size="small"
        label={toUiDateString(date)}
    />;