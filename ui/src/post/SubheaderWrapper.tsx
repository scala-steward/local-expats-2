import {FC, PropsWithChildren} from "react";
import Typography from "@mui/material/Typography";

export const SubheaderWrapper: FC<PropsWithChildren> = ({children}) =>
    <Typography variant="caption" sx={{display: 'flex', alignItems: 'center'}}>
        {children}
    </Typography>
