import {FC} from "react";
import {getPostUrl, PostDto} from "./PostDto";
import Typography from "@mui/material/Typography";
import Link from "next/link";

type PostTitleProps = {
    post: PostDto
}

export const PostTitle: FC<PostTitleProps> = ({post}) =>
    <Link href={getPostUrl(post)}>
        <Typography variant="h6" sx={{cursor: "pointer"}}>
            {post.title}
        </Typography>
    </Link>;