import {CardMedia} from "@mui/material";
import {useSmallScreen} from "../util/useUtils";
import {FC} from "react";

type ImageDisplayProps = {
    image: string | undefined;
};

export const ImageDisplay: FC<ImageDisplayProps> = ({image}) => {
    const smallScreen = useSmallScreen();
    return (
        image
            ?
            <CardMedia
                component="img"
                height={smallScreen ? 240 : 400}
                image={image}
                sx={{objectFit: "contain", p:1/4}}
                loading="lazy"
            />
            : null
    );
};