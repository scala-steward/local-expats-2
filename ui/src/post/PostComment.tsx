import Badge, {BadgeProps} from "@mui/material/Badge";
import IconButton from "@mui/material/IconButton";
import AddCommentIcon from "@mui/icons-material/AddComment";
import {FC} from "react";
import {styled} from "@mui/material";
import {PostLink} from "./PostLink";
import {PostOnlyProps} from "./PostOnlyProps";

const StyledBadge = styled(Badge)<BadgeProps>(({theme}) => ({
    '& .MuiBadge-badge': {
        right: 3,
        top: 15,
        border: `2px solid ${theme.palette.background.paper}`,
        padding: '0 4px',
    },
}));

export const PostComment: FC<PostOnlyProps> = ({post}) =>
    <PostLink post={post}>
        <StyledBadge badgeContent={post.noOfComments} color="secondary" sx={{mr: 1}}>
            <IconButton>
                <AddCommentIcon color="action"/>
            </IconButton>
        </StyledBadge>
    </PostLink>;