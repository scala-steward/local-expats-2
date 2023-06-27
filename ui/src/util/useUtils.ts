import {useMediaQuery, useTheme} from "@mui/material";

export const useIsSmallScreen = () =>
    useMediaQuery(useTheme().breakpoints.down('sm'));
