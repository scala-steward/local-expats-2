import {FC} from "react";
import {PostDto} from "./PostDto";
import {useInfiniteQuery} from "@tanstack/react-query";
import {Post} from "./Post";
import InfiniteScroll from "react-infinite-scroll-component";
import {Loading} from "../util/Loading";
import {useSelectedState} from "../location/SelectedState";
import {get} from "../util/Fetch";

export const Posts: FC = () => {
    const pageSize = 10;

    const {selectedState: state} = useSelectedState();

    const fetchPosts = ({pageParam: lastId = undefined}): Promise<PostDto[]> => {
        const params = {
            state,
            pageSize,
            lastId,
        }
        return get('api/posts', params);
    };

    const {
        isLoading,
        data,
        error,
        fetchNextPage,
        hasNextPage,
        isFetchingNextPage,
    } = useInfiniteQuery(['posts', state], fetchPosts, {
        getNextPageParam: (lastPage) => lastPage.length === pageSize ? lastPage[lastPage.length - 1]?.id : undefined,
    })

    if (isLoading) {
        return (<Loading/>);
    }

    if (error) {
        console.error(error)
    }

    const posts = data?.pages.flat() || [];

    return (
        <InfiniteScroll
            dataLength={posts.length}
            next={fetchNextPage}
            hasMore={hasNextPage || isFetchingNextPage}
            loader={<Loading/>}
            endMessage={
                <p style={{textAlign: 'center'}}>
                    <b>Yay! you're all caught up.</b>
                </p>
            }
        >
            {posts.map(post => <Post key={post.id} post={post}/>)}
        </InfiniteScroll>
    );
}