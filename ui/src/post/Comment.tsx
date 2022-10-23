import {FC} from "react";
import {CommentDto} from "./CommentDto";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import {CommentSubheader} from "./CommentSubheader";
import {ImageDisplay} from "./ImageDisplay";

type CommentProps = {
    comment: CommentDto
}

export const Comment: FC<CommentProps> = ({comment}) =>
    <Card sx={{m: 1}} variant="outlined">
        <CardHeader sx={{my: -1}}
                    subheader={<CommentSubheader comment={comment}/>}
        />
        <CardContent sx={{mt: -3, mb: -2}}>
            <Typography variant="body1" sx={{whiteSpace: "pre-line"}}>
                {comment.message}
            </Typography>
        </CardContent>
        <ImageDisplay image={comment.image}/>
    </Card>;