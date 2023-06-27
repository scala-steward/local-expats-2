import Link from "next/link";
import Add from "@mui/icons-material/Add";
import {FC} from "react";
import {NavIcon} from "./NavIcon";

export const AddNewNavIcon: FC = () =>
    <Link href="/new" legacyBehavior>
        <NavIcon>
            <Add/>
        </NavIcon>
    </Link>;