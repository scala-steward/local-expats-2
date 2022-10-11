import {FC} from "react";
import {PostDto} from "./PostDto";
import Typography from "@mui/material/Typography";

type PostSubheaderProps = {
    post: PostDto
}

export const PostSubheader: FC<PostSubheaderProps> = ({post}) => {
    const postedDate = new Date(post.createdAt).toLocaleDateString();
    const postInfo = `posted in ${post.state} on ${postedDate}`;

    return (
        <Typography variant={"caption"}>
            {postInfo}
        </Typography>
    );
}