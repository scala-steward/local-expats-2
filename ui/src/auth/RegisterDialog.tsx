import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import {Dialog, DialogContent, DialogTitle, IconButton} from '@mui/material';
import {Close} from '@mui/icons-material';
import {useForm} from 'react-hook-form';
import {useAuth} from "./Auth";
import Box from "@mui/material/Box";
import {useIsSmallScreen} from "../util/useUtils";
import {post} from "../util/Fetch";
import {UserWithAuthTokenResponse} from "./User";

interface RegisterDialogProps {
    open: boolean;
    onClose: () => void;
}

type RegisterPayload = {
    firstName: string,
    lastName: string,
    email: string,
    password: string
};

export default function RegisterDialog({open, onClose}: RegisterDialogProps) {
    const isSmallScreen = useIsSmallScreen();
    const {updateAuth} = useAuth();

    const {register, handleSubmit, control, formState: {errors}} = useForm<RegisterPayload>();
    const onSubmit = (data: RegisterPayload) => {
        post<UserWithAuthTokenResponse>('/api/users', data)
            .then(updateAuth)
            .then(onClose);
    }

    return (
        <Dialog
            open={open}
            onClose={onClose}
            fullScreen={isSmallScreen}
        >
            <DialogTitle sx={{padding: '0 10px', textAlign: 'end', lineHeight: '1rem'}}>
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
                        Register
                    </Typography>
                    <Box component="form" noValidate onSubmit={handleSubmit(onSubmit)} sx={{mt: 3}}>
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    autoComplete="given-name"
                                    variant="outlined"
                                    required
                                    fullWidth
                                    label="First Name"
                                    autoFocus
                                    {...register('firstName', {required: true})}
                                    error={!!errors.firstName}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    variant="outlined"
                                    required
                                    fullWidth
                                    label="Last Name"
                                    autoComplete="family-name"
                                    {...register('lastName', {required: true})}
                                    error={!!errors.lastName}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    variant="outlined"
                                    required
                                    fullWidth
                                    label="Email Address"
                                    autoComplete="email"
                                    {...register('email', {required: true})}
                                    error={!!errors.email}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    variant="outlined"
                                    required
                                    fullWidth
                                    label="Password"
                                    type="password"
                                    autoComplete="new-password"
                                    {...register('password', {required: true})}
                                    error={!!errors.password}
                                />
                            </Grid>
                        </Grid>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            sx={{mt: 3, mb: 2}}
                        >
                            Register
                        </Button>
                    </Box>
                </Box>
            </DialogContent>
        </Dialog>
    );
}