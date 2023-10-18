import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable} from "rxjs";
import {ApiResponse, PagingResponse, SortBy} from "../types/api-response.type";
import {CreatePost, Post, PostList, UpdatePost} from "../types/post.type";
import {environment} from "../../../environments/environment";
import {handleError} from "../../shared/commons/handle-error-http";
import {User} from "../types/user.type";

@Injectable({
    providedIn: "root"
})
export class PostService{
    constructor(private http:HttpClient) {

    }

    getPostDetailBySlug(slug:string):Observable<ApiResponse<Post>>{
        return this.http.get<ApiResponse<Post>>(`${environment.apiUrl}/posts/slug/${slug}`).pipe(
            catchError(handleError)
        )
    }

    getAllPostByTag(keyword:string,slug:string,pageIndex:number,sortBy:SortBy):Observable<ApiResponse<PagingResponse<PostList[]>>>{
        return this.http.get<ApiResponse<PagingResponse<PostList[]>>>(`${environment.apiUrl}/posts/${slug}/tags?pageIndex=${pageIndex}&sortBy=${sortBy}&keyword=${keyword}`).pipe(
            catchError(handleError)
        )
    }

    getAllPost(keyword:string,pageIndex:number,sortBy:SortBy):Observable<ApiResponse<PagingResponse<PostList[]>>>{
        return this.http.get<ApiResponse<PagingResponse<PostList[]>>>(`${environment.apiUrl}/posts/?pageIndex=${pageIndex}&sortBy=${sortBy}&keyword=${keyword}`).pipe(
            catchError(handleError)
        )
    }

    getAllPostNotPublished(keyword:string,pageIndex:number,sortBy:SortBy):Observable<ApiResponse<PagingResponse<PostList[]>>>{
        return this.http.get<ApiResponse<PagingResponse<PostList[]>>>(`${environment.apiUrl}/posts/not-published?pageIndex=${pageIndex}&sortBy=${sortBy}&keyword=${keyword}`).pipe(
            catchError(handleError)
        )
    }

    getAllPostOfAuthorByUserName(keyword:string,userName:string,pageIndex:number,sortBy:SortBy):Observable<ApiResponse<PagingResponse<PostList[]>>>{
        return this.http.get<ApiResponse<PagingResponse<PostList[]>>>(`${environment.apiUrl}/posts/${userName}?pageIndex=${pageIndex}&sortBy=${sortBy}&keyword=${keyword}`).pipe(
            catchError(handleError)
        )
    }

    getAllPostByCurrentUser(keyword:string,pageIndex:number,sortBy:SortBy):Observable<ApiResponse<PagingResponse<PostList[]>>>{
        return this.http.get<ApiResponse<PagingResponse<PostList[]>>>(`${environment.apiUrl}/posts/user?pageIndex=${pageIndex}&sortBy=${sortBy}&keyword=${keyword}`).pipe(
            catchError(handleError)
        )
    }

    addPostToSeries(postId:number,seriesId:number):Observable<ApiResponse<boolean>>{
        return this.http.post<ApiResponse<boolean>>(`${environment.apiUrl}/posts/${postId}/series/${seriesId}`,{}).pipe(
            catchError(handleError)
        )
    }

    likePost(postId:number):Observable<ApiResponse<boolean>>{
        return this.http.post<ApiResponse<boolean>>(`${environment.apiUrl}/posts/${postId}/likes`,{}).pipe(
            catchError(handleError)
        )
    }

    unlikePost(postId:number):Observable<ApiResponse<boolean>>{
        return this.http.delete<ApiResponse<boolean>>(`${environment.apiUrl}/posts/${postId}/likes`,{}).pipe(
            catchError(handleError)
        )
    }

    getAllUserLikePost(postId:number):Observable<ApiResponse<User>>{
        return this.http.get<ApiResponse<User>>(`${environment.apiUrl}/posts/${postId}/likes`).pipe(
            catchError(handleError)
        )
    }

    createPost(data:CreatePost):Observable<ApiResponse<PostList>>{
        return this.http.post<ApiResponse<PostList>>(`${environment.apiUrl}/posts/`,data).pipe(
            catchError(handleError)
        )
    }

    updatePost(data:UpdatePost,postId:number):Observable<ApiResponse<PostList>>{
        return this.http.put<ApiResponse<PostList>>(`${environment.apiUrl}/posts/${postId}`,data).pipe(
            catchError(handleError)
        )
    }

    deletePost(postId:number):Observable<ApiResponse<boolean>>{
        return this.http.delete<ApiResponse<boolean>>(`${environment.apiUrl}/posts/${postId}`).pipe(
            catchError(handleError)
        )
    }
}