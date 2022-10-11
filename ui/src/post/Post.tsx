import {FC} from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import CardActionArea from '@mui/material/CardActionArea';
import CardHeader from '@mui/material/CardHeader';
import {PostDto} from "./PostDto";
import {PostSubheader} from "./PostSubheader";

type PostProps = {
    post: PostDto
};

export const Post: FC<PostProps> = ({post}) => {

    return (
        <Card sx={{margin: 1}} variant="outlined">
            <CardActionArea>
                <CardHeader
                    title={post.title}
                    titleTypographyProps={{
                        variant: "h6",
                    }}
                    subheader={<PostSubheader post={post}/>}
                />
                {
                    post.message &&
                    (
                        <CardContent sx={{mt: -3}}>
                            <Typography
                                variant="body1"
                            >
                                {post.message}
                            </Typography>
                        </CardContent>
                    )
                }
            </CardActionArea>
        </Card>
    );
};