import {Injectable} from "@angular/core";
import {catchError, Observable} from "rxjs";
import {ApiResponse, PagingResponse, SortBy} from "../types/api-response.type";
import {CreateTag, Tag, UpdateTag} from "../types/tag.type";
import {environment} from "../../../environments/environment";
import {handleError} from "../../shared/commons/handle-error-http";
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: "root"
})
export class TagService{
    constructor(private http:HttpClient) {
    }
    getAllTag():Observable<ApiResponse<Tag[]>>{
        return this.http.get<ApiResponse<Tag[]>>(`${environment.apiUrl}/tags/all`).pipe(
            catchError(handleError)
        )
    }

    getListTag(pageIndex:number,keyword:string,sortBy:SortBy):Observable<ApiResponse<PagingResponse<Tag[]>>>{
        return this.http.get<ApiResponse<PagingResponse<Tag[]>>>(`${environment.apiUrl}/tags/?pageIndex=${pageIndex}&keyword=${keyword}&sortBy=${sortBy}`).pipe(
            catchError(handleError)
        )
    }

    getTagBySlug(slug:string):Observable<ApiResponse<Tag>>{
        return this.http.get<ApiResponse<Tag>>(`${environment.apiUrl}/tags/${slug}`).pipe(
            catchError(handleError)
        )
    }

    createTag(data:CreateTag):Observable<ApiResponse<Tag>>{
        return this.http.post<ApiResponse<Tag>>(`${environment.apiUrl}/tags/`,data).pipe(
            catchError(handleError)
        )
    }

    updateTag(data:UpdateTag,tagId:number):Observable<ApiResponse<Tag>>{
        return this.http.put<ApiResponse<Tag>>(`${environment.apiUrl}/tags/${tagId}`,data).pipe(
            catchError(handleError)
        )
    }

    deleteTag(tagId:number):Observable<ApiResponse<Tag>>{
        return this.http.delete<ApiResponse<Tag>>(`${environment.apiUrl}/tags/${tagId}`).pipe(
            catchError(handleError)
        )
    }
}