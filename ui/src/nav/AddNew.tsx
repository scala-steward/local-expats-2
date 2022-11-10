import Link from "next/link";
import IconButton from "@mui/material/IconButton";
import Add from "@mui/icons-material/Add";
import {FC} from "react";

export const AddNew: FC = () => <Link href="/new" legacyBehavior>
    <IconButton
        size="large"
        color="inherit"
    >
        <Add/>
    </IconButton>
</Link>;