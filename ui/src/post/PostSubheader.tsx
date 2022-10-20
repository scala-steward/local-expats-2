import {FC} from "react";
import {DateChip} from "./DateChip";
import {LocationChip} from "./LocationChip";
import {RandomAvatar} from "./RandomAvatar";
import {SubheaderWrapper} from "./SubheaderWrapper";
import {PostOnlyProps} from "./PostOnlyProps";

export const PostSubheader: FC<PostOnlyProps> = ({post}) =>
    <SubheaderWrapper>
        <RandomAvatar seed={post.id}/>
        posted in
        <LocationChip locationId={post.locationId}/>
        on
        <DateChip date={post.createdAt}/>
    </SubheaderWrapper>;
