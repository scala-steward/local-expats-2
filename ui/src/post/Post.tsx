import {FC} from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import CardHeader from '@mui/material/CardHeader';
import {PostDto} from "./PostDto";
import {PostSubheader} from "./PostSubheader";
import {CardActions} from "@mui/material";
import {CommentDto} from "./CommentDto";
import {Comment} from "./Comment";
import {AddComment} from "./AddComment";
import {PostTitle} from "./PostTitle";
import Link from "next/link";
import {PostWithCommentsDto} from "./PostWithCommentsDto";

type PostProps = {
    post: PostDto;
    comments?: CommentDto[];
    onCommentedAdded?: (post: PostWithCommentsDto) => void;
};

export const Post: FC<PostProps> = ({
    post,
    comments,
    onCommentedAdded,
}) => {

    const showComments = comments !== undefined;

    return (
        <Card sx={{margin: 1}} variant="outlined">
            <CardHeader
                title={<PostTitle post={post}/>}
                subheader={<PostSubheader post={post}/>}
            />
            {
                post.message &&
                (
                    <Link href={`/posts/${post.id}`}>
                        <CardContent sx={{mt: -3}}>
                            <Typography
                                variant="body1"
                            >
                                {post.message}
                            </Typography>
                        </CardContent>
                    </Link>
                )
            }

            {showComments &&
                comments.map(comment => <Comment key={comment.id} comment={comment}/>)}

            {showComments &&
                <CardActions>
                    <AddComment postId={post.id} onCommentedAdded={onCommentedAdded}/>
                </CardActions>
            }

        </Card>
    );
};