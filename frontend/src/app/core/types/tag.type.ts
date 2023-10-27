export interface Tag{
    id: number,
    title:string,
    slug:string,
    createdAt:string,
    updatedAt:string
}

export interface CreateTag{
    title:string,
    slug:string,
}


export interface UpdateTag{
    title:string,
    slug:string,
}