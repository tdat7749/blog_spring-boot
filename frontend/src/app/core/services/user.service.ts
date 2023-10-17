import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {ApiResponse} from "../types/api-response.type";
import {environment} from "../../../environments/environment";
import {ChangeInformation, ChangePassword, ForgotPassword} from "../types/user.type";
import {handleError} from "../../shared/commons/handle-error-http";

@Injectable({
    providedIn:"root"
})

export class UserService{
    constructor(private http:HttpClient) {
    
    }

    getEmailForgotPassword(email:string):Observable<ApiResponse<boolean>>{
        return this.http.get<ApiResponse<boolean>>(`${environment.apiUrl}/users/${email}/forgot-mail`).pipe(
            catchError(handleError)
        )
    }
    
    forgotPassword(data:ForgotPassword):Observable<ApiResponse<boolean>>{
        return this.http.patch<ApiResponse<boolean>>(`${environment.apiUrl}/users/forgot`,data).pipe(
            catchError(handleError)
        )
    }

    changePassword(data:ChangePassword):Observable<ApiResponse<boolean>>{
        return this.http.patch<ApiResponse<boolean>>(`${environment.apiUrl}/users/password`,data).pipe(
            catchError(handleError)
        )
    }

    changeInformation(data:ChangeInformation):Observable<ApiResponse<boolean>>{
        return this.http.patch<ApiResponse<boolean>>(`${environment.apiUrl}/users/information`,data).pipe(
            catchError(handleError)
        )
    }

    changeAvatar(avatar:string):Observable<ApiResponse<boolean>>{
        return this.http.patch<ApiResponse<boolean>>(`${environment.apiUrl}/users/avatar`,avatar).pipe(
            catchError(handleError)
        )
    }
}