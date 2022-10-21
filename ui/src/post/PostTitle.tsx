import {FC} from "react";
import Typography from "@mui/material/Typography";
import {PostOnlyProps} from "./PostOnlyProps";
import {PostLink} from "./PostLink";

export const PostTitle: FC<PostOnlyProps> = ({post}) =>
    <PostLink post={post}>
        <Typography variant="h6" sx={{cursor: "pointer"}}>
            {post.title}
        </Typography>
    </PostLink>;