import {FC, PropsWithChildren} from "react";
import IconButton, {IconButtonProps} from "@mui/material/IconButton";

export const NavIcon: FC<PropsWithChildren<IconButtonProps>> = ({children, ...props}) =>
    <IconButton
        size="large"
        color="inherit"
        {...props}
    >
        {children}
    </IconButton>