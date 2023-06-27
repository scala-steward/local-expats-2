import {FC} from "react";
import Link from "next/link";
import Chip from "@mui/material/Chip";
import {LocationId, useSelectedLocation} from "../location/SelectedLocation";
import {getLocationLabel} from "../location/LocationDto";
import Tooltip from "@mui/material/Tooltip";
import {useIsSmallScreen} from "../util/useUtils";

export const LocationChip: FC<{ locationId: LocationId }> = ({locationId}) => {
    const {isLoading, getLocation, setSelectedLocationId} = useSelectedLocation();
    const locationLabel = isLoading ? '' : getLocationLabel(getLocation(locationId));
    const isSmallScreen = useIsSmallScreen();
    return (
        <Link href="/" legacyBehavior>
            <Tooltip title={locationLabel}>
                <Chip
                    clickable
                    sx={{mx: 1}}
                    style={{
                        ...(isSmallScreen && {maxWidth: 110})
                    }}
                    color="primary"
                    label={locationLabel}
                    variant="outlined"
                    size="small"
                    onClick={() => setSelectedLocationId(locationId)}
                />
            </Tooltip>
        </Link>
    );
}