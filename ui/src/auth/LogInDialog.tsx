import React, {FC} from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import {Dialog, DialogContent, DialogTitle, IconButton} from "@mui/material";
import {Close} from "@mui/icons-material";
import {useForm} from "react-hook-form";
import {useAuth} from "./Auth";
import Box from "@mui/material/Box";
import {useIsSmallScreen} from "../util/useUtils";
import {post} from "../util/Fetch";
import {UserWithAuthTokenResponse} from "./User";
import Grid from "@mui/material/Grid";
import RegisterLink from "./RegisterLink";

type LogInPayload = {
    email: string,
    password: string,
};

interface LogInDialogProps {
    open: boolean;
    onClose: () => void;
}

export const LogInDialog: FC<LogInDialogProps> = ({open, onClose}: LogInDialogProps) => {
    const isSmallScreen = useIsSmallScreen();
    const {updateAuth} = useAuth();

    const {register, handleSubmit, formState: {errors}} = useForm<LogInPayload>();
    const onSubmit = (data: LogInPayload) => {
        post<UserWithAuthTokenResponse>('/api/users/login', data)
            .then(updateAuth)
            .then(onClose);
    }

    return (
        <Dialog
            open={open}
            onClose={onClose}
            fullScreen={isSmallScreen}
        >
            <DialogTitle sx={{padding: '0 10px', textAlign: 'end', lineHeight: '1rem',}}>
                <IconButton edge="end" color="inherit" onClick={onClose} aria-label="close">
                    <Close/>
                </IconButton>
            </DialogTitle>
            <DialogContent>
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                        <LockOutlinedIcon/>
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Log In
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit(onSubmit)} noValidate sx={{mt: 1}}>
                        <TextField
                            autoFocus
                            margin="normal"
                            required
                            fullWidth
                            id="email"
                            label="Email Address"
                            autoComplete="email"
                            {...register('email', {required: true})}
                            error={!!errors.email}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                            {...register('password', {required: true})}
                            error={!!errors.password}
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{mt: 3, mb: 2}}
                        >
                            Log In
                        </Button>
                    </Box>
                    <Grid container sx={{mt: 1}}>
                        <Grid item xs>
                            <RegisterLink/>
                        </Grid>
                    </Grid>
                </Box>
            </DialogContent>
        </Dialog>
    );
}