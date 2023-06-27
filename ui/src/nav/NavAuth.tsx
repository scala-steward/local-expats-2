import {FC} from "react";
import {LogInNavIcon} from "./LogInNavIcon";
import {AuthStatus, useAuth} from "../auth/Auth";
import {LoadingNavIcon} from "./LoadingNavIcon";
import {NavAccount} from "./NavAccount";

export const NavAuth: FC = () => {
    const {status} = useAuth();

    switch (status) {

    case AuthStatus.UNAUTHENTICATED:
        return <LogInNavIcon/>

    case AuthStatus.LOADING:
        return <LoadingNavIcon/>;

    case AuthStatus.AUTHENTICATED:
        return <NavAccount/>;

    default:
        throw new Error(`Unknown AuthStatus: ${AuthStatus[status]}`);
    }
}