import {NavIcon} from "./NavIcon";
import {Login} from "@mui/icons-material";
import React, {FC, useState} from "react";
import {LogInDialog} from "../auth/LogInDialog";

export const LogInNavIcon: FC = () => {

    const [logInModalOpen, setLogInModalOpen] = useState(false);

    const openLogInModal = () => {
        setLogInModalOpen(true);
    };

    const handleLogInModalClose = () => {
        setLogInModalOpen(false);
    }

    return (
        <>
            <NavIcon onClick={openLogInModal}>
                <Login/>
            </NavIcon>
            <LogInDialog open={logInModalOpen} onClose={handleLogInModalClose}/>
        </>
    );

};