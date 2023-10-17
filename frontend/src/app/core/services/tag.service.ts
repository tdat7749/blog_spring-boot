import {Injectable} from "@angular/core";
import {catchError, Observable} from "rxjs";
import {ApiResponse} from "../types/api-response.type";
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
    getAllTag():Observable<ApiResponse<Tag>>{
        return this.http.get<ApiResponse<Tag>>(`${environment.apiUrl}/tags`).pipe(
            catchError(handleError)
        )
    }

    createTag(data:CreateTag):Observable<ApiResponse<Tag>>{
        return this.http.post<ApiResponse<Tag>>(`${environment.apiUrl}/tags`,data).pipe(
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