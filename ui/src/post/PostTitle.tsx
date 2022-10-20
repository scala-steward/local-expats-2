import {FC} from "react";
import {getPostUrl} from "./PostDto";
import Typography from "@mui/material/Typography";
import Link from "next/link";
import {PostOnlyProps} from "./PostOnlyProps";

export const PostTitle: FC<PostOnlyProps> = ({post}) =>
    <Link href={getPostUrl(post)}>
        <Typography variant="h6" sx={{cursor: "pointer"}}>
            {post.title}
        </Typography>
    </Link>;