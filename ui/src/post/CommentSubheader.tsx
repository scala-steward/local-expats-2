import {FC} from "react";
import {CommentDto} from "./CommentDto";
import Typography from "@mui/material/Typography";
import {DateChip} from "./DateChip";

type CommentSubheaderProps = {
    comment: CommentDto
}

export const CommentSubheader: FC<CommentSubheaderProps> = ({comment}) =>
    <Typography variant="caption">
        commented on
        <DateChip date={comment.createdAt}/>
    </Typography>
