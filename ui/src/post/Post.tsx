import {FC} from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import CardHeader from '@mui/material/CardHeader';
import {PostDto} from "./PostDto";
import {PostSubheader} from "./PostSubheader";
import {CardActions, CardMedia} from "@mui/material";
import {CommentDto} from "./CommentDto";
import {Comment} from "./Comment";
import {AddComment} from "./AddComment";
import {PostTitle} from "./PostTitle";
import {PostWithCommentsDto} from "./PostWithCommentsDto";
import {PostShare} from "./PostShare";
import {PostBookmark} from "./PostBookmark";
import {PostComment} from "./PostComment";
import {PostLink} from "./PostLink";
import {useSmallScreen} from "../util/useUtils";

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
    const smallScreen = useSmallScreen();
    return (
        <Card sx={{m: 1}} variant="outlined">
            <CardHeader
                title={<PostTitle post={post}/>}
                subheader={<PostSubheader post={post}/>}
            />
            {
                post.message &&
                <PostLink post={post}>
                    <CardContent sx={{mt: -2, cursor: "pointer"}}>
                        <Typography
                            variant="body1"
                            sx={{
                                whiteSpace: "pre-line"
                            }}
                        >
                            {post.message}
                        </Typography>
                    </CardContent>
                </PostLink>
            }
            {
                post.image &&
                <CardMedia
                    component="img"
                    height={smallScreen ? 240 : 400}
                    image={post.image}
                    sx={{objectFit: "contain"}}
                />
            }
            <CardActions sx={{display: 'flex', gap: 2}}>
                <PostComment post={post}/>
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