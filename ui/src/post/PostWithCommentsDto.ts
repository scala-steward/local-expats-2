import {PostDto} from "./PostDto";
import {CommentDto} from "./CommentDto";

export type PostWithCommentsDto = {
    post: PostDto;
    comments: CommentDto[];
}