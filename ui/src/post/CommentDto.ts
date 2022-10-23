export type CommentDto = {
    id: number;
    postId: number;
    message: string;
    image?: string;
    createdAt: string;
}