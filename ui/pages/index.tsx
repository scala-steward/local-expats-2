import * as React from 'react';
import type {NextPage} from 'next';
import {PostDto} from "../src/post/PostDto";
import {Post} from "../src/post/Post";
import {useQuery} from "@tanstack/react-query";

const Home: NextPage = () => {
    const fetchPosts = (): Promise<PostDto[]> => fetch('api/posts')
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw response.statusText;
            }
        });

    const {isLoading, error, data: posts} = useQuery(["posts"], fetchPosts);

    if (isLoading) {
        return (<span>Loading...</span>);
    }

    if (error) {
        return (<span>{`An error has occurred: ${error}`}</span>)
    }

    return (
        <>
            {
                posts?.map(post => <Post key={post.id} post={post}/>)
            }
        </>
    );
};

export default Home;
