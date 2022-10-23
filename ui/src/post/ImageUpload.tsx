import React, {FC, useState} from "react";
import {Avatar, Stack} from "@mui/material";
import IconButton from "@mui/material/IconButton";
import {Close, Image, PhotoCamera} from "@mui/icons-material";
import Badge from "@mui/material/Badge";

type ImageUploadProps = {
    onUpload: (link: string) => void;
}

export const ImageUpload: FC<ImageUploadProps> = ({onUpload}) => {
    const [image, setImage] = useState(null);

    const handleFileUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
        const fileList = event.target.files
        const file = fileList?.item(0);
        if (!file) {
            return;
        }
        const formData = new FormData();
        formData.append("image", file)
        // Imgur doesn't support upload from localhost, use 0.0.0.0 instead.
        fetch("https://api.imgur.com/3/image/", {
            method: "post",
            headers: {
                Authorization: "Client-ID 88313496bf12a3e" // YOLO
            },
            body: formData
        })
            .then(response => response.json())
            .then(response => {
                if (response.success) {
                    const link = response.data.link;
                    setImage(link);
                    onUpload(link);
                } else {
                    console.error(response);
                }
            })
    }
    return (
        <Stack direction="row"
               alignItems="center"
               spacing={2}
        >
            <IconButton color="primary" aria-label="upload picture" component="label">
                <input hidden accept="image/*" type="file" onChange={handleFileUpload}/>
                <PhotoCamera fontSize="large"/>
            </IconButton>
            {image &&
                <Avatar variant="rounded" src={image}>
                    <Image/>
                </Avatar>
            }

        </Stack>
    );
};