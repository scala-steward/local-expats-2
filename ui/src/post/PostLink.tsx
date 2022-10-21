import {FC, PropsWithChildren} from "react";
import {getPostUrl} from "./PostDto";
import Link from "next/link";
import {PostOnlyProps} from "./PostOnlyProps";

export const PostLink: FC<PostOnlyProps & PropsWithChildren> = ({post, children}) =>
    <Link href={getPostUrl(post)}>
        {children}
    </Link>;