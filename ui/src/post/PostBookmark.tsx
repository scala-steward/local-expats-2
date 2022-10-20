import IconButton from "@mui/material/IconButton";
import {BookmarkAdd, BookmarkAdded} from "@mui/icons-material";
import {FC} from "react";
import {PostOnlyProps} from "./PostOnlyProps";
import {usePostBookmarks} from "./PostBookmarks";

export const PostBookmark: FC<PostOnlyProps> = ({post}) => {
    const {isBookmarked, toggleBookmark} = usePostBookmarks();
    return (
        <IconButton onClick={() => toggleBookmark(post.id)}>
            {
                isBookmarked(post.id) ? <BookmarkAdded/> : <BookmarkAdd/>
            }
        </IconButton>
    );
};