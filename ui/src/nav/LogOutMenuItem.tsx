import {MenuItem} from "@mui/material";
import React, {FC} from "react";
import {useAuth} from "../auth/Auth";
import {Logout} from "@mui/icons-material";
import {NavIcon} from "./NavIcon";

export const LogOutMenuItem: FC = () =>
    <MenuItem onClick={useAuth().removeAuth}>
        <NavIcon>
            <Logout/>
        </NavIcon>
        <p>Log Out</p>
    </MenuItem>;