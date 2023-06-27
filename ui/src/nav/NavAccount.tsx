import React, {FC, useState} from "react";
import IconButton from "@mui/material/IconButton";
import {AccountCircle, Mail, Notifications} from "@mui/icons-material";
import {Menu, MenuItem} from "@mui/material";
import Badge from "@mui/material/Badge";

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
                <MenuItem>
                    <IconButton size="large" aria-label="show 4 new mails" color="inherit">
                        <Badge badgeContent={4} color="error">
                            <Mail />
                        </Badge>
                    </IconButton>
                    <p>Messages</p>
                </MenuItem>
                <MenuItem>
                    <IconButton
                        size="large"
                        aria-label="show 17 new notifications"
                        color="inherit"
                    >
                        <Badge badgeContent={17} color="error">
                            <Notifications />
                        </Badge>
                    </IconButton>
                    <p>Notifications</p>
                </MenuItem>
            </Menu>
        </>
    );
}