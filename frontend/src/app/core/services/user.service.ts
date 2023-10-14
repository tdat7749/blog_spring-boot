import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {ApiResponse} from "../types/api-response.type";
import {environment} from "../../../environments/environment";
import {ChangeInformation, ChangePassword, ForgotPassword} from "../types/user.type";

@Injectable({
    providedIn:"root"
})

export class UserService{
    constructor(private http:HttpClient) {
    
    }

    getEmailForgotPassword(email:string):Observable<ApiResponse<boolean>>{
        return this.http.get<ApiResponse<boolean>>(`${environment.apiUrl}/users/${email}/forgot-mail`).pipe(
            catchError(this.handleError)
        )
    }
    
    forgotPassword(data:ForgotPassword):Observable<ApiResponse<boolean>>{
        return this.http.patch<ApiResponse<boolean>>(`${environment.apiUrl}/users/forgot`,data).pipe(
            catchError(this.handleError)
        )
    }

    changePassword(data:ChangePassword):Observable<ApiResponse<boolean>>{
        return this.http.patch<ApiResponse<boolean>>(`${environment.apiUrl}/users/password`,data).pipe(
            catchError(this.handleError)
        )
    }

    changeInformation(data:ChangeInformation):Observable<ApiResponse<boolean>>{
        return this.http.patch<ApiResponse<boolean>>(`${environment.apiUrl}/users/information`,data).pipe(
            catchError(this.handleError)
        )
    }

    private handleError(error: HttpErrorResponse){
        if(error.status === 0){
            console.error("An error occurred: ",error.error)
        }else{
            console.error(
                `Backend returned code ${error.status}, body was: `, error.error);
        }

        return throwError(() => error.error.message)
    }
}