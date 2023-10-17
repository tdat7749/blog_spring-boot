export interface Tag{
    id: number,
    title:string,
    slug:string,
    thumbnail:string | undefined,
    createdAt:string,
    updatedAt:string
}

export interface CreateTag{
    title:string,
    slug:string,
    thumbnail:string
}


export interface UpdateTag{
    title:string,
    slug:string,
    thumbnail?:string
}