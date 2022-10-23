import React, {FC} from "react";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {Controller, useForm} from "react-hook-form";
import Box from "@mui/material/Box";
import {PostWithCommentsDto} from "./PostWithCommentsDto";
import {post} from "../util/Fetch";
import {usePostBookmarks} from "./PostBookmarks";
import {ImageUpload} from "./ImageUpload";

type AddCommentDto = {
    message: string;
    image?: string;
}

type AddCommentProps = {
    postId: number
    onCommentedAdded?: (post: PostWithCommentsDto) => void;
}

export const AddComment: FC<AddCommentProps> = ({postId, onCommentedAdded}) => {
    const {register, handleSubmit, formState: {errors}, reset, control} = useForm<AddCommentDto>();
    const {addBookmark} = usePostBookmarks();

    const onSubmit = (comment: AddCommentDto) =>
        post<PostWithCommentsDto>(`/api/posts/${postId}/comments`, comment)
            .then(data => {
                reset()
                addBookmark(data.post.id, true);
                onCommentedAdded && onCommentedAdded(data);
            });

    return (
        <Box component="form" noValidate onSubmit={handleSubmit(onSubmit)} sx={{flexGrow: 1, mt: 0}}>
            <Grid container spacing={1}>
                <Grid item xs={12}>
                    <TextField
                        variant="outlined"
                        required
                        fullWidth
                        multiline
                        minRows={2}
                        maxRows={6}
                        autoComplete="off"
                        {...register('message', {required: true, minLength: 2})}
                        error={!!errors.message}
                    />
                </Grid>
                <Grid item xs={12}>
                    <Controller
                        name="image"
                        control={control}
                        render={({field: {onChange}}) =>
                            <ImageUpload onUpload={(link) => {
                                onChange(link);
                            }}/>
                        }
                    />
                </Grid>

                <Grid item xs={12} sx={{mb: 2}}>
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                    >
                        Comment
                    </Button>
                </Grid>
            </Grid>
        </Box>
    );
}