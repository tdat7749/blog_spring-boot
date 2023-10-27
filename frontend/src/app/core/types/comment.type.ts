import { UserAbs } from "./user.type";

export interface Comment {
    id: number,
    parentId: number,
    content: string,
    postId: number,
    user: UserAbs,
    childComment: Comment[],
    showReplyForm: boolean,
    showEditForm: boolean,
    createdAt: string
}

export interface CreateComment {
    parentId?: number,
    content: string
}

export interface EditComment {
    id: number,
    content: string
}