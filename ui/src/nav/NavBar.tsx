import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Link from "next/link";
import {LocationSelect} from "../location/LocationSelect";
import {useSelectedLocation} from "../location/SelectedLocation";
import {AddNew} from "./AddNew";
import {Notification} from "./Notification";

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
                            sx={{
                                cursor: "pointer",
                                caretColor: "transparent"
                            }}
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
                        <Notification/>
                        <AddNew/>
                    </Box>
                </Toolbar>
            </AppBar>
            <Toolbar/> {/* This Toolbar will occupy the space underneath the "fixed" Appbar and prevent the content to be hidden underneath Appbar. */}
        </Box>
    );
}