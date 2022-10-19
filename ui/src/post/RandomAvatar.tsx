import {FC} from "react";
import {Avatar} from "@mui/material";
import {RandomIcon} from "./RandomIcon";
import {
    amber,
    blue,
    blueGrey,
    brown,
    common,
    cyan,
    deepOrange,
    deepPurple,
    green,
    grey,
    indigo,
    lightBlue,
    lightGreen,
    lime,
    orange,
    pink,
    purple,
    red,
    teal,
    yellow,
} from "@mui/material/colors";

const colors = [
    amber,
    blue,
    blueGrey,
    brown,
    common,
    cyan,
    deepOrange,
    deepPurple,
    green,
    grey,
    indigo,
    lightBlue,
    lightGreen,
    lime,
    orange,
    pink,
    purple,
    red,
    teal,
    yellow,
];

export const RandomAvatar: FC<{ seed: number; }> = ({seed}) => {
    const color = colors[seed % colors.length];
    // @ts-ignore
    const bgcolor = color[500];
    return (
        <Avatar variant="rounded"
                sx={{
                    bgcolor,
                    height: 24, width: 24,
                    mr: 1 / 2
                }}>
            <RandomIcon seed={seed}/>
        </Avatar>
    );
};