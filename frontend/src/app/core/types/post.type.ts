import {CreateTag, Tag} from "./tag.type";
import {UserAbs} from "./user.type";
import {Series} from "./series.type";

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
    published:boolean,
    tags: Tag[],
    author: UserAbs,
    series:Series
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
    content:string,
    isPublished:boolean,
    series:Series
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
    published:boolean,
}

export interface UpdatePostStatus{
    status:boolean
}