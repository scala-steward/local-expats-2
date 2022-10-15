import {FC} from "react";
import {PostDto} from "./PostDto";
import {useInfiniteQuery} from "@tanstack/react-query";
import {Post} from "./Post";
import InfiniteScroll from "react-infinite-scroll-component";
import {Loading} from "../util/Loading";
import {get} from "../util/Fetch";
import {useSelectedLocation} from "../location/SelectedLocation";

export const Posts: FC = () => {
    const pageSize = 10;

    const {selectedLocation} = useSelectedLocation();
    const locationId = selectedLocation?.id;

    const fetchPosts = ({pageParam: lastId = undefined}): Promise<PostDto[]> => {
        const params = {
            locationId,
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
    } = useInfiniteQuery(['posts', locationId], fetchPosts, {
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