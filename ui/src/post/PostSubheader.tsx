import {FC} from "react";
import {PostDto} from "./PostDto";
import Typography from "@mui/material/Typography";
import {DateChip} from "./DateChip";
import {LocationChip} from "./LocationChip";
import {RandomAvatar} from "./RandomAvatar";
import Box from "@mui/material/Box";
import {SubheaderWrapper} from "./SubheaderWrapper";
import {SharePost} from "./SharePost";

type PostSubheaderProps = {
    post: PostDto
}

export const PostSubheader: FC<PostSubheaderProps> = ({post}) =>
    <SubheaderWrapper>
        <RandomAvatar seed={post.id}/>
        posted in
        <LocationChip locationId={post.locationId}/>
        on
        <DateChip date={post.createdAt}/>
        <Box sx={{flexGrow: 1}}/>
        <SharePost post={post}/>
    </SubheaderWrapper>;
