import {createContext, FC, PropsWithChildren, useContext} from "react";
import {useLocalStorage} from "usehooks-ts";
import {xor} from "lodash";

type PostId = number;
type UsePostBookmarks = {
    isBookmarked: (postId: PostId) => boolean
    toggleBookmark: (postId: PostId) => void
}

const PostBookmarksContext = createContext<UsePostBookmarks | undefined>(undefined);

export const PostBookmarksProvider: FC<PropsWithChildren> = ({children}) => {
    // Use IndexedDB if performance issue
    const [storageBookmarks, setStorageBookmarks] = useLocalStorage("bookmarks", "[]");

    // use zod or io-ts to validate
    // @ts-ignore
    const postIds: PostId[] = JSON.parse(storageBookmarks);
    const postIdsSet = new Set(postIds);

    const isBookmarked = (postId: PostId) => postIdsSet.has(postId);
    const toggleBookmark = (postId: PostId) => {
        const newPostIds = xor(postIds, [postId])
        setStorageBookmarks(JSON.stringify(newPostIds));
    }

    return (
        <PostBookmarksContext.Provider value={{
            isBookmarked,
            toggleBookmark
        }}>
            {children}
        </PostBookmarksContext.Provider>
    );
}

export const usePostBookmarks = (): UsePostBookmarks => {
    const UsePostBookmarksContext = useContext(PostBookmarksContext);
    if (UsePostBookmarksContext === undefined) {
        throw new Error('usePostBookmarks must be used within a PostBookmarksProvider');
    }
    return UsePostBookmarksContext;
}