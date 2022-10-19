import {LocationId} from "../location/SelectedLocation";

export interface PostDto {
    id: number;
    title: string;
    message?: string;
    locationId: LocationId;
    createdAt: string;
}

export const getPostUrl = (post: PostDto) => `/posts/${post.id}`;