import IconButton from "@mui/material/IconButton";
import {Badge} from "@mui/material";
import {Notifications} from "@mui/icons-material";
import {FC} from "react";
import {get} from "../util/Fetch";
import {PostDto} from "../post/PostDto";
import {useQuery} from "@tanstack/react-query";
import {usePostBookmarks} from "../post/PostBookmarks";

export const Notification: FC = () => {
    const {postIds} = usePostBookmarks();
    const ids = postIds.join(',');
    const fetchUpdatedPosts = () => get<PostDto[]>('/api/posts/updated', {
        ids,
        since: new Date().toISOString(),
    });
    const {isLoading, data: updates} = useQuery(['notifications', ids], fetchUpdatedPosts, {
        refetchOnWindowFocus: false,
        refetchOnMount: false,
        refetchOnReconnect: false,
        refetchInterval: 3 * 60 * 1000, // 3 minutes
    });

    return (
        <IconButton
            size="large"
            color="inherit"
        >
            <Badge badgeContent={updates?.length} color="info">
                <Notifications/>
            </Badge>
        </IconButton>
    );
}