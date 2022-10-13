import {FC} from "react";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {useForm} from "react-hook-form";
import Box from "@mui/material/Box";
import {PostWithCommentsDto} from "./PostWithCommentsDto";

type AddCommentDto = {
    message: string;
}

type AddCommentProps = {
    postId: number
    onCommentedAdded?: (post: PostWithCommentsDto) => void;
}

export const AddComment: FC<AddCommentProps> = ({postId, onCommentedAdded}) => {
    const {register, handleSubmit, formState: {errors}, reset} = useForm<AddCommentDto>();
    const onSubmit = (data: AddCommentDto) =>
        fetch(`/api/posts/${postId}/comments`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data),
            }
        )
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw response.statusText;
                }
            })
            .then(post => {
                reset()
                onCommentedAdded && onCommentedAdded(post);
            });

    return (
        <Box component="form" noValidate onSubmit={handleSubmit(onSubmit)} sx={{flexGrow: 1, mt: 0}}>
            <Grid container spacing={1}>
                <Grid item xs={12}>
                    <TextField
                        variant="outlined"
                        required
                        fullWidth
                        autoComplete="off"
                        {...register('message', {required: true, minLength: 2})}
                        error={!!errors.message}
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