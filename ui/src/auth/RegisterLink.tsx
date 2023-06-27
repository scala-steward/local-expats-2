import {Button} from "@mui/material";
import React, {useState} from "react";
import RegisterDialog from "./RegisterDialog";
import Link from "@mui/material/Link";

export default function RegisterLink() {
    const [registerModalOpen, setRegisterModalOpen] = useState(false);

    const openRegisterModal = () => {
        setRegisterModalOpen(true);
    };

    const handleRegisterModalClose = () => {
        setRegisterModalOpen(false);
    }

    return (
        <>
            <Link component="button" variant="body2" onClick={openRegisterModal}>
                New to this community? Register to join
            </Link>
            <RegisterDialog open={registerModalOpen} onClose={handleRegisterModalClose}/>
        </>
    )
}