import {FC} from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import {CardActionArea} from '@mui/material';
import {PostDto} from "./PostDto";

type PostProps = {
    post: PostDto
};

export const Post: FC<PostProps> = ({post}) => (
    <Card sx={{margin: 1}} variant="outlined">
        <CardActionArea>
            <CardContent>
                <Typography gutterBottom variant="h6" component="div">
                    {post.title}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    {post.message}
                </Typography>
            </CardContent>
        </CardActionArea>
    </Card>
);