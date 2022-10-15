import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Add from '@mui/icons-material/Add';
import Link from "next/link";
import {LocationSelect} from "../location/LocationSelect";
import {useSelectedLocation} from "../location/SelectedLocation";

export default function NavBar() {
    const {selectedLocation, setSelectedLocation, setSelectedLocationToDefault} = useSelectedLocation();
    return (
        <Box>
            <AppBar position="fixed">
                <Toolbar>
                    <Link href="/">
                        <Typography
                            variant="h6"
                            noWrap
                            sx={{cursor: "pointer"}}
                            onClick={setSelectedLocationToDefault}
                        >
                            NepaliUS
                        </Typography>
                    </Link>
                    <Box sx={{
                        flexGrow: 1,
                        mx: {xs: 1, sm: 2},
                        width: 140
                    }}>
                        <LocationSelect
                            value={selectedLocation}
                            onChange={setSelectedLocation}
                        />
                    </Box>
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