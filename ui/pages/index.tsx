import * as React from 'react';
import type {NextPage} from 'next';
import {PostDto} from "../src/post/PostDto";
import {Post} from "../src/post/Post";

const Home: NextPage = () => {
    const posts: PostDto[] = [
        {
            "id": 7,
            "title": "Where are you going?",
            "message": "I want to come along.",
            "targetState": "VA",
            "targetZipCode": "23294",
            "createdAt": "2022-10-06T02:39:22.878823Z"
        },
        {
            "id": 6,
            "title": "How can Help?",
            "message": "I can help very well.",
            "targetState": "VA",
            "targetZipCode": "23294",
            "createdAt": "2022-10-06T02:39:22.878823Z"
        },
        {
            "id": 5,
            "title": "What do you do?",
            "message": "I am just curious.",
            "targetState": "VA",
            "targetZipCode": "23294",
            "createdAt": "2022-10-06T02:39:22.878823Z"
        },
        {
            "id": 4,
            "title": "Where do you live?",
            "message": "I want to move. Where are Nepalese people?",
            "targetState": "VA",
            "targetZipCode": "23294",
            "createdAt": "2022-10-06T02:39:22.878823Z"
        },
        {
            "id": 3,
            "title": "Three Three Three",
            "message": "Hello From Three",
            "targetState": "VA",
            "targetZipCode": "23294",
            "createdAt": "2022-10-06T02:39:22.878823Z"
        },
        {
            "id": 2,
            "title": "Two Two",
            "message": "Hello 2",
            "targetState": "VA",
            "targetZipCode": "23294",
            "createdAt": "2022-10-06T02:38:43.73811Z"
        },
        {
            "id": 1,
            "title": "One 1",
            "message": "One!!!",
            "targetState": "VA",
            "targetZipCode": "23294",
            "createdAt": "2022-10-06T02:38:10.106644Z"
        }
    ];
    return (
        <>
            {
                posts.map(post => <Post key={post.id} post={post}/>)
            }
        </>
    );
};

export default Home;
