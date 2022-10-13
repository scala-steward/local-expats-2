import {FC} from "react";
import {useQuery} from "@tanstack/react-query";
import {Loading} from "../util/Loading";
import {Post} from "./Post";
import {PostWithCommentsDto} from "./PostWithCommentsDto";

type PostDetailProps = {
    postId: number
}

export const PostDetail: FC<PostDetailProps> = ({postId}) => {

    const fetchPost = (): Promise<PostWithCommentsDto> => {
        return fetch(`/api/posts/${postId}`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw response.statusText;
                }
            });
    };

    const {isLoading, data: postWithComments, refetch} = useQuery(['posts', postId], fetchPost);
    if (isLoading || !postWithComments) {
        return <Loading/>
    }
    return <Post post={postWithComments.post} comments={postWithComments.comments} onCommentedAdded={() => refetch()}/>;
}