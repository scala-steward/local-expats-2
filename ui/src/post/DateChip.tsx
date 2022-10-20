import Chip from "@mui/material/Chip";
import {FC} from "react";
import {toUiDateString} from "../util/Utils";

export const DateChip: FC<{ date: string }> = ({date}) =>
    <Chip
        sx={{ml: 1}}
        variant="filled"
        size="small"
        label={toUiDateString(date)}
    />;