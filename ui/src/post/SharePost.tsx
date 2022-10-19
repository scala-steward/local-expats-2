import {FC} from "react";
import {getPostUrl, PostDto} from "./PostDto";
import Share from "@mui/icons-material/Share";
import IconButton from "@mui/material/IconButton";
import {isHttps} from "../util/Utils";

export const SharePost: FC<{ post: PostDto }> = ({post}) => {
    // navigator.share is only available in https
    // Show Share in development
    const hideShare = !navigator.share && isHttps();
    const share = async () => {
        if (navigator.share) {
            await navigator.share({
                url: getPostUrl(post),
                title: post.title
            });
        } else {
            console.error("Web Share API is available only in https context.")
        }
    }

    return hideShare ? null : (
        <IconButton onClick={share} sx={{mx: -1 / 2}}>
            <Share fontSize="small"/>
        </IconButton>
    );
}