import {useMediaQuery, useTheme} from "@mui/material";

export const useSmallScreen = () =>
    useMediaQuery(useTheme().breakpoints.down('sm'));
