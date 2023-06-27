import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import {LocationSelect} from "../location/LocationSelect";
import {useSelectedLocation} from "../location/SelectedLocation";
import {AddNewNavIcon} from "./AddNewNavIcon";
import IconButton from "@mui/material/IconButton";
import {AppIcon} from "./AppIcon";
import Link from "next/link";
import {useIsSmallScreen} from "../util/useUtils";
import Typography from "@mui/material/Typography";
import {Container} from "@mui/material";
import {NavAuth} from "./NavAuth";

export default function NavBar() {
    const {selectedLocation, setSelectedLocation, setSelectedLocationToDefault} = useSelectedLocation();

    const smallScreen = useIsSmallScreen();
    return (
        <Box>
            <AppBar position="fixed">
                <Container maxWidth="md" disableGutters>
                    <Toolbar>
                        <Link href="/" legacyBehavior>
                            <Box onClick={setSelectedLocationToDefault} sx={{display: "flex", alignItems: "center"}}>
                                <IconButton>
                                    <AppIcon fontSize="large"/>
                                </IconButton>
                                {
                                    !smallScreen && (
                                        <Typography
                                            variant="h6"
                                            noWrap
                                            sx={{
                                                cursor: "pointer",
                                                caretColor: "transparent"
                                            }}

                                        >
                                            NepaliUS
                                        </Typography>
                                    )
                                }
                            </Box>
                        </Link>


                        <Box sx={{
                            flexGrow: 1,
                            ml: {xs: 1, sm: 3},
                            maxWidth: '350px'
                        }}>
                            <LocationSelect
                                value={selectedLocation}
                                onChange={setSelectedLocation}
                            />
                        </Box>
                        {!smallScreen && <Box sx={{flexGrow: 1}}/>}
                        <Box sx={{display: {sm: 'flex'}, ml: 2}}>
                            <AddNewNavIcon/>
                            <NavAuth/>
                        </Box>
                    </Toolbar>
                </Container>
            </AppBar>
            <Toolbar/> {/* This Toolbar will occupy the space underneath the "fixed" Appbar and prevent the content to be hidden underneath Appbar. */}
        </Box>
    );
}