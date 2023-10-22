import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ApiResponse, PagingResponse, SortBy} from "../types/api-response.type";
import { environment } from "src/environments/environment";
import {Comment, CreateComment, EditComment} from "../types/comment.type";

@Injectable({
    providedIn: "root"
})

export class CommentService{
    constructor(private http:HttpClient) {
    }

    getCommentPost(slug:string,pageIndex:number,sortBy:SortBy):Observable<ApiResponse<PagingResponse<Comment[]>>>{
        return this.http.get<ApiResponse<PagingResponse<Comment[]>>>(`${environment.apiUrl}/comments/${slug}/posts?pageIndex=${pageIndex}&sortBy=${sortBy}`)
    }


    createComment(data:CreateComment,postId:number):Observable<ApiResponse<Comment>>{
        return this.http.post<ApiResponse<Comment>>(`${environment.apiUrl}/comments/${postId}/posts`,data)
    }

    editComment(data:EditComment):Observable<ApiResponse<Comment>>{
        return this.http.patch<ApiResponse<Comment>>(`${environment.apiUrl}/comments/`,data)
    }

    deleteComment(id:number):Observable<ApiResponse<boolean>>{
        return this.http.delete<ApiResponse<boolean>>(`${environment.apiUrl}/comments/${id}`)
    }
}