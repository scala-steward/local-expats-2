import {LocationId} from "../location/SelectedLocation";
import {replaceNonAlphaNumeric} from "../util/Utils";

export interface PostDto {
    id: number;
    title: string;
    message?: string;
    locationId: LocationId;
    createdAt: string;
}

const getTitleSlug = (post: PostDto) =>
    replaceNonAlphaNumeric(post.title, "-").substring(0, 50);

export const getPostUrl = (post: PostDto) => `/posts/${post.id}/${getTitleSlug(post)}`;