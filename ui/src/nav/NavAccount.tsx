import React, {FC, useState} from "react";
import IconButton from "@mui/material/IconButton";
import {AccountCircle} from "@mui/icons-material";
import {Menu} from "@mui/material";
import {LogOutMenuItem} from "./LogOutMenuItem";
import {NotificationsMenuItem} from "./NotificationsMenuItem";

export const NavAccount: FC = () => {
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

    const isMenuOpen = Boolean(anchorEl);

    const handleMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget)
    }

    const handleMenuClose = () => {
        setAnchorEl(null);
    }

    return (
        <>
            <IconButton
                size="large"
                color="inherit"
                onClick={handleMenuOpen}
            >
                <AccountCircle/>
            </IconButton>
            <Menu
                anchorEl={anchorEl}
                anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                keepMounted
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                open={isMenuOpen}
                onClose={handleMenuClose}
            >
                <NotificationsMenuItem/>
                <LogOutMenuItem/>
            </Menu>
        </>
    );
}