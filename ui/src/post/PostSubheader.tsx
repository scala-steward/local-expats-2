import {FC} from "react";
import {PostDto} from "./PostDto";
import Typography from "@mui/material/Typography";
import {DateChip} from "./DateChip";
import {StateChip} from "./StateChip";

type PostSubheaderProps = {
    post: PostDto
}

export const PostSubheader: FC<PostSubheaderProps> = ({post}) =>
    <Typography variant="caption">
        posted in
        <StateChip state={post.state}/>
        on
        <DateChip date={post.createdAt}/>
    </Typography>;
