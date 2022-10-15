import {FC} from "react";
import Link from "next/link";
import Chip from "@mui/material/Chip";
import {LocationId, useSelectedLocation} from "../location/SelectedLocation";
import {getLocationLabel} from "../location/LocationDto";

export const LocationChip: FC<{ locationId: LocationId }> = ({locationId}) => {
    const {getLocation, setSelectedLocationId} = useSelectedLocation();
    const locationLabel = getLocationLabel(getLocation(locationId));
    return (
        <Link href="/">
            <Chip
                sx={{mx: 1}}
                clickable
                color="primary"
                label={locationLabel}
                variant="outlined"
                size="small"
                onClick={() => setSelectedLocationId(locationId)}
            />
        </Link>
    );
}