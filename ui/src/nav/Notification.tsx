import IconButton from "@mui/material/IconButton";
import {Badge, ListItemIcon, ListItemText, Menu, MenuItem} from "@mui/material";
import {Notifications, QuestionAnswer} from "@mui/icons-material";
import React, {FC, useState} from "react";
import {get} from "../util/Fetch";
import {PostDto} from "../post/PostDto";
import {useQuery} from "@tanstack/react-query";
import {usePostBookmarks} from "../post/PostBookmarks";
import Tooltip from "@mui/material/Tooltip";
import {PostLink} from "../post/PostLink";
import {useSmallScreen} from "../util/useUtils";

export const Notification: FC = () => {
    const {postIds} = usePostBookmarks();
    const ids = postIds.join(',');
    const fetchUpdatedPosts = () => get<PostDto[]>('/api/posts/updated', {
        ids,
        since: new Date().toISOString(),
    });
    const {data} = useQuery(['notifications', ids], fetchUpdatedPosts, {
        refetchOnWindowFocus: false,
        refetchOnMount: false,
        refetchOnReconnect: false,
        refetchInterval: 3 * 60 * 1000, // 3 minutes
    });
    const updatedPosts = data ?? [];

    const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
    const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorElUser(event.currentTarget);
    };
    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    const smallScreen = useSmallScreen();

    return (
        <>
            <Tooltip title="Show notifications">
                <IconButton onClick={handleOpenUserMenu}
                            size="large"
                            color="inherit"
                >
                    <Badge badgeContent={updatedPosts.length} color="info">
                        <Notifications/>
                    </Badge>
                </IconButton>
            </Tooltip>
            <Menu
                sx={{mt: 5}}
                anchorEl={anchorElUser}
                keepMounted
                anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                open={Boolean(anchorElUser)}
                onClose={handleCloseUserMenu}
                PaperProps={{
                    style: {
                        width: smallScreen ? '100%' : '350px'
                    }
                }}
            >
                {updatedPosts.map((post) => (
                    <PostLink post={post} key={post.id}>
                        <MenuItem onClick={handleCloseUserMenu}>
                            <ListItemIcon>
                                <Badge badgeContent={2} color="info">
                                    <QuestionAnswer fontSize="small"/>
                                </Badge>
                            </ListItemIcon>
                            <ListItemText secondary={post.title} secondaryTypographyProps={{noWrap: true}}/>
                        </MenuItem>
                    </PostLink>
                ))}
            </Menu>
        </>
    );
}