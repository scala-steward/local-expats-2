import {createContext, FC, PropsWithChildren, useContext, useEffect} from "react";
import {useLocalStorage} from "usehooks-ts";
import {xor} from "lodash";

type PostId = number;
type UsePostBookmarks = {
    postIds: readonly PostId[];
    isBookmarked: (postId: PostId) => boolean;
    toggleBookmark: (postId: PostId) => void;
    addBookmark: (postId: PostId, updateNotificationsLastCheckedDate: boolean) => void;
    notificationsLastChecked: string;
    updateNotificationsLastChecked: () => void;
}

const getCurrentDateIso = () => new Date().toISOString();

const PostBookmarksContext = createContext<UsePostBookmarks | undefined>(undefined);

export const PostBookmarksProvider: FC<PropsWithChildren> = ({children}) => {
    const defaultBookmarks = "[]";
    // Use IndexedDB if performance issue
    const [storageBookmarks, setStorageBookmarks] = useLocalStorage("bookmarks", defaultBookmarks);
    const [notificationsLastChecked, setNotificationsLastChecked] = useLocalStorage('notificationsLastChecked', "");

    useEffect(() => {
        if (notificationsLastChecked === "") {
            setNotificationsLastChecked(getCurrentDateIso());
        }
    }, [notificationsLastChecked, setNotificationsLastChecked]);

    const parseBookmarkedPostIds = (): PostId[] => {
        // @ts-ignore
        try {
            // use zod to validate
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
    const addBookmark = (postId: PostId, updateNotificationsLastCheckedDate: boolean) => {
        if (!isBookmarked(postId)) {
            toggleBookmark(postId);
        }
        if (updateNotificationsLastCheckedDate) {
            updateNotificationsLastChecked();
        }
    }

    const updateNotificationsLastChecked = () => setNotificationsLastChecked(getCurrentDateIso);

    return (
        <PostBookmarksContext.Provider value={{
            postIds,
            isBookmarked,
            toggleBookmark,
            addBookmark,
            notificationsLastChecked,
            updateNotificationsLastChecked,
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