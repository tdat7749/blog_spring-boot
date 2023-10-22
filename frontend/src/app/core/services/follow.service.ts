import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable} from "rxjs";
import {ApiResponse} from "../types/api-response.type";
import {environment} from "../../../environments/environment";
import {handleError} from "../../shared/commons/handle-error-http";

@Injectable({
    providedIn: "root"
})

export class FollowService{
    constructor(private http:HttpClient) {

    }

    follow(id:number):Observable<ApiResponse<boolean>>{
        return this.http.post<ApiResponse<boolean>>(`${environment.apiUrl}/follows/${id}`,{}).pipe(
            catchError(handleError)
        )
    }

    unFollow(id:number):Observable<ApiResponse<boolean>>{
        return this.http.delete<ApiResponse<boolean>>(`${environment.apiUrl}/follows/${id}`,{}).pipe(
            catchError(handleError)
        )
    }

    checkFollowed(userName:string):Observable<ApiResponse<boolean>>{
        return this.http.get<ApiResponse<boolean>>(`${environment.apiUrl}/follows/${userName}`).pipe(
            catchError(handleError)
        )
    }
}