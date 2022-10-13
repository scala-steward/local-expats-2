import {FC} from "react";
import {CommentDto} from "./CommentDto";
import Typography from "@mui/material/Typography";
import {DateChip} from "./DateChip";
import {RandomAvatar} from "./RandomAvatar";

type CommentSubheaderProps = {
    comment: CommentDto
}

export const CommentSubheader: FC<CommentSubheaderProps> = ({comment}) =>
    <Typography variant="caption">
        <RandomAvatar seed={comment.id}/>
        commented on
        <DateChip date={comment.createdAt}/>
    </Typography>
