import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, catchError, Observable, Subject} from "rxjs";
import {ApiResponse, PagingResponse, SortBy} from "../types/api-response.type";
import {environment} from "../../../environments/environment";
import {ChangeInformation, ChangePassword, ForgotPassword, User} from "../types/user.type";
import {handleError} from "../../shared/commons/handle-error-http";
import {Notification, RpNotification} from "../types/noti.type";

@Injectable({
    providedIn:"root"
})

export class UserService{

    notificationState$: BehaviorSubject<RpNotification | null> = new BehaviorSubject<RpNotification | null>(null)
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

    getAuthor(userName:string):Observable<ApiResponse<User>>{
        return this.http.get<ApiResponse<User>>(`${environment.apiUrl}/users/${userName}`).pipe(
            catchError(handleError)
        )
    }

    getListFollowing(userName:string,pageIndex:number,sortBy:SortBy):Observable<ApiResponse<PagingResponse<User[]>>>{
        return this.http.get<ApiResponse<PagingResponse<User[]>>>(`${environment.apiUrl}/users/${userName}/follower?pageIndex=${pageIndex}&sortBy=${sortBy}`).pipe(
            catchError(handleError)
        )
    }
    getListFollowers(userName:string,pageIndex:number,sortBy:SortBy):Observable<ApiResponse<PagingResponse<User[]>>>{
        return this.http.get<ApiResponse<PagingResponse<User[]>>>(`${environment.apiUrl}/users/${userName}/following?pageIndex=${pageIndex}&sortBy=${sortBy}`).pipe(
            catchError(handleError)
        )
    }

    getTop10Notification():Observable<ApiResponse<RpNotification>>{
        return this.http.get<ApiResponse<RpNotification>>(`${environment.apiUrl}/users/top10-noti`).pipe(
            catchError(handleError)
        )
    }

    getAllNotification(pageIndex:number):Observable<ApiResponse<PagingResponse<RpNotification>>>{
        return this.http.get<ApiResponse<PagingResponse<RpNotification>>>(`${environment.apiUrl}/users/notifications?pageIndex=${pageIndex}`).pipe(
            catchError(handleError)
        )
    }

    readNotification(id:number):Observable<ApiResponse<boolean>>{
        return this.http.patch<ApiResponse<boolean>>(`${environment.apiUrl}/users/${id}/notifications`,{}).pipe(
            catchError(handleError)
        )
    }

    readAllNotification():Observable<ApiResponse<boolean>>{
        return this.http.patch<ApiResponse<boolean>>(`${environment.apiUrl}/users/notifications`,{}).pipe(
            catchError(handleError)
        )
    }
}