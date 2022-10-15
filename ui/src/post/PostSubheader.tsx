import {FC} from "react";
import {PostDto} from "./PostDto";
import Typography from "@mui/material/Typography";
import {DateChip} from "./DateChip";
import {LocationChip} from "./LocationChip";
import {RandomAvatar} from "./RandomAvatar";

type PostSubheaderProps = {
    post: PostDto
}

export const PostSubheader: FC<PostSubheaderProps> = ({post}) =>
    <Typography variant="caption">
        <RandomAvatar seed={post.id}/>
        posted in
        <LocationChip locationId={post.locationId}/>
        on
        <DateChip date={post.createdAt}/>
    </Typography>;
