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
import {PostWithCommentsDto} from "./PostWithCommentsDto";
import {PostShare} from "./PostShare";
import {PostBookmark} from "./PostBookmark";
import {PostLink} from "./PostLink";

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
        <Card sx={{m: 1}} variant="outlined">
            <CardHeader
                title={<PostTitle post={post}/>}
                subheader={<PostSubheader post={post}/>}
            />
            <PostLink post={post}>
                <CardContent sx={{my: -3, cursor: "pointer"}}>
                    {post.message && <Typography
                        variant="body1"
                        sx={{
                            whiteSpace: "pre-line"
                        }}
                    >
                        {post.message}
                    </Typography>}
                </CardContent>
            </PostLink>
            <CardActions>
                <PostBookmark post={post}/>
                <PostShare post={post}/>
            </CardActions>

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