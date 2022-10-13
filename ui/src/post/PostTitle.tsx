import {FC} from "react";
import {PostDto} from "./PostDto";
import Typography from "@mui/material/Typography";
import Link from "next/link";

type PostTitleProps = {
    post: PostDto
}

export const PostTitle: FC<PostTitleProps> = ({post}) =>
    <Link href={`/posts/${post.id}`}>
        <Typography variant="h6">
            {post.title}
        </Typography>
    </Link>;