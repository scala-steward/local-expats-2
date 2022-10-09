import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Add from '@mui/icons-material/Add';
import Link from "next/link";

export default function NavBar() {
    return (
        <Box sx={{flexGrow: 1}}>
            <AppBar position="fixed">
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="open drawer"
                        sx={{mr: 2}}
                    >
                        <Link href="/">
                            <Typography
                                variant="h6"
                                noWrap
                                component="div"
                            >
                                NepaliUS
                            </Typography>
                        </Link>
                    </IconButton>
                    <Box sx={{flexGrow: 1}}/>
                    <Box sx={{display: {sm: 'flex'}, ml: 2}}>
                        <IconButton
                            size="large"
                            aria-label="add new"
                            color="inherit"
                        >
                            <Link href="/new">
                                <Add/>
                            </Link>
                        </IconButton>
                    </Box>
                </Toolbar>
            </AppBar>
            <Toolbar/> {/* This Toolbar will occupy the space underneath the "fixed" Appbar and prevent the content to be hidden underneath Appbar. */}
        </Box>
    );
}