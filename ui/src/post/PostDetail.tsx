import {FC} from "react";
import {useQuery} from "@tanstack/react-query";
import {Loading} from "../util/Loading";
import {Post} from "./Post";
import {PostWithCommentsDto} from "./PostWithCommentsDto";
import {get} from "../util/Fetch";

type PostDetailProps = {
    postId: number
}

export const PostDetail: FC<PostDetailProps> = ({postId}) => {

    const fetchPost = () => get<PostWithCommentsDto>(`/api/posts/${postId}`);

    const {isLoading, data: postWithComments, refetch} = useQuery(['posts', 'id', postId], fetchPost);
    if (isLoading || !postWithComments) {
        return <Loading/>
    }
    return <Post post={postWithComments.post} comments={postWithComments.comments} onCommentedAdded={() => refetch()}/>;
}