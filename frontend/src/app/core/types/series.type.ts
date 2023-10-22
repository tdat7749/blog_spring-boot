import {PostList} from "./post.type";

export interface Series{
    id:number,
    title:string,
    slug:string,
    content:string,
    updatedAt:string,
    createdAt:string
}

export interface SeriesListPost{
    id:number,
    title:string,
    slug:string,
    content:string,
    updatedAt:string,
    createdAt:string,
    posts: PostList[]
}

export interface CreateSeries{
    title:string,
    content:string,
    slug:string
}

export interface UpdateSeries{
    title:string,
    content:string,
    slug:string
}