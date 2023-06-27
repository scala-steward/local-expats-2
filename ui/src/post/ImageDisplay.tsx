import {CardMedia} from "@mui/material";
import {useIsSmallScreen} from "../util/useUtils";
import {FC} from "react";

type ImageDisplayProps = {
    image: string | undefined;
};

export const ImageDisplay: FC<ImageDisplayProps> = ({image}) => {
    const isSmallScreen = useIsSmallScreen();
    return (
        image
            ?
            <CardMedia
                component="img"
                height={isSmallScreen ? 240 : 400}
                image={image}
                sx={{objectFit: "contain", p:1/4}}
                loading="lazy"
            />
            : null
    );
};