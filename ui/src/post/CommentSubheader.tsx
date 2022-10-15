import {FC} from "react";
import {CommentDto} from "./CommentDto";
import {DateChip} from "./DateChip";
import {RandomAvatar} from "./RandomAvatar";
import {SubheaderWrapper} from "./SubheaderWrapper";

type CommentSubheaderProps = {
    comment: CommentDto
}

export const CommentSubheader: FC<CommentSubheaderProps> = ({comment}) =>
    <SubheaderWrapper>
        <RandomAvatar seed={comment.id}/>
        commented on
        <DateChip date={comment.createdAt}/>
    </SubheaderWrapper>
