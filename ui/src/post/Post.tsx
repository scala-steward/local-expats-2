import {FC} from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import CardActionArea from '@mui/material/CardActionArea';
import CardHeader from '@mui/material/CardHeader';
import {PostDto} from "./PostDto";

type PostProps = {
    post: PostDto
};

export const Post: FC<PostProps> = ({post}) => {
    const postedDate = new Date(post.createdAt).toLocaleDateString();
    const postInfo = `posted in ${post.state} on ${postedDate}`;
    return (
        <Card sx={{margin: 1}} variant="outlined">
            <CardActionArea>
                <CardHeader
                    title={post.title}
                    titleTypographyProps={{
                        variant: "h6",
                    }}
                    subheader={postInfo}
                    subheaderTypographyProps={{
                        variant: "caption"
                    }}
                />
                <CardContent sx={{mt: -3}}>
                    <Typography
                        variant="body1"
                    >
                        {post.message}
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
};