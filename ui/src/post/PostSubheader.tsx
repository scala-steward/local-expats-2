import {FC} from "react";
import {PostDto} from "./PostDto";
import Typography from "@mui/material/Typography";
import {useSelectedState} from "../location/SelectedState";
import {stateLabels} from "../nav/State";
import Chip from "@mui/material/Chip";

type PostSubheaderProps = {
    post: PostDto
}

export const PostSubheader: FC<PostSubheaderProps> = ({post}) => {
    const {setSelectedState} = useSelectedState();
    const postedDate = new Date(post.createdAt).toLocaleDateString();
    return (
        <Typography variant="caption">
            posted in
            <Chip
                sx={{mx: 1}}
                clickable
                color="primary"
                label={stateLabels[post.state]}
                variant="outlined"
                size="small"
                onClick={() => setSelectedState(post.state)}
            />
            on
            <Chip
                sx={{mx: 1}}
                variant="filled"
                size="small"
                label={postedDate}
            />
        </Typography>
    );
}