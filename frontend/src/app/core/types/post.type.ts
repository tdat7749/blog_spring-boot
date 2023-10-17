import {CreateTag, Tag} from "./tag.type";
import {UserAbs} from "./user.type";

export interface PostCardInfo{
    avatar:string,
    title:string,
    totalComment:number,
    firstName:string,
    lastName:string,
    createdAt:string,
    totalLike:number
}

export interface PostList{
    id:number,
    title:string,
    slug:string,
    createdAt:string,
    updatedAt:string,
    thumbnail:string,
    summary:string,
    totalComment:number,
    totalLike:number,
    totalView:number,
    tags: Tag[],
    author: UserAbs
}

export interface Post{
    id:number,
    title:string,
    slug:string,
    createdAt:string,
    updatedAt:string,
    thumbnail:string,
    summary:string,
    totalComment:number,
    totalLike:number,
    totalView:number,
    tags: Tag[],
    author: UserAbs,
    content:string
}

export interface CreatePost{
    title:string,
    content:string,
    slug:string,
    summary:string,
    thumbnail:string,
    seriesId?:number,
    listTags:CreateTag[]
}

export interface UpdatePost{
    title:string,
    content:string,
    slug:string,
    summary:string,
    thumbnail?:string,
    seriesId?:number,
    listTags:CreateTag[],
    isPublished:boolean,
}

export interface UpdatePostStatus{
    status:boolean
}