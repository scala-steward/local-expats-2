import {createContext, FC, PropsWithChildren, useContext} from "react";
import {useLocalStorage} from "usehooks-ts";
import {xor} from "lodash";

type PostId = number;
type UsePostBookmarks = {
    postIds: readonly PostId[];
    isBookmarked: (postId: PostId) => boolean;
    toggleBookmark: (postId: PostId) => void;
    addBookmark: (postId: PostId) => void;
}

const PostBookmarksContext = createContext<UsePostBookmarks | undefined>(undefined);

export const PostBookmarksProvider: FC<PropsWithChildren> = ({children}) => {
    const defaultBookmarks = "[]";
    // Use IndexedDB if performance issue
    const [storageBookmarks, setStorageBookmarks] = useLocalStorage("bookmarks", defaultBookmarks);

    const parseBookmarkedPostIds = (): PostId[] => {
        // @ts-ignore
        try {
            // use zod or io-ts to validate
            return JSON.parse(storageBookmarks) as PostId[];
        } catch (error) {
            console.error(error);
            setStorageBookmarks(defaultBookmarks)
            return [];
        }
    };

    const postIds: PostId[] = parseBookmarkedPostIds();
    const postIdsSet = new Set(postIds);

    const isBookmarked = (postId: PostId) => postIdsSet.has(postId);
    const toggleBookmark = (postId: PostId) => {
        const newPostIds = xor(postIds, [postId])
        setStorageBookmarks(JSON.stringify(newPostIds));
    }
    const addBookmark = (postId: PostId) => {
        if (!isBookmarked(postId)) {
            toggleBookmark(postId);
        }
    }

    return (
        <PostBookmarksContext.Provider value={{
            postIds,
            isBookmarked,
            toggleBookmark,
            addBookmark,
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