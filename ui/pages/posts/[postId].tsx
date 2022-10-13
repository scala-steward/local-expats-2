import {useRouter} from 'next/router'
import {PostDetail} from "../../src/post/PostDetail";
import {NextPage} from "next";
import {Loading} from "../../src/util/Loading";

const PostDetailPage: NextPage = () => {
    const router = useRouter()
    const {postId} = router.query
    if (!postId || isNaN(Number(postId))) {
        return <Loading/>;
    }
    return <PostDetail postId={Number(postId)}/>;
}

export default PostDetailPage;