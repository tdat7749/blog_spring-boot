import {inject, Injectable} from "@angular/core";
import {User} from "../types/user.type";
import {BehaviorSubject, catchError, Observable, of, throwError} from "rxjs";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ApiResponse} from "../types/api-response.type";
import {environment} from "../../../environments/environment";
import {Login, Register, VerifyEmail} from "../types/auth.type";
import {Token} from "../types/token.type";
import {CookieService} from "ngx-cookie-service";
import {MessageService} from "primeng/api";
import {handleError} from "../../shared/commons/handle-error-http";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  userState: User | null = null;

  userState$: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null)
  constructor(private http: HttpClient,private userService:UserService,private cookieService: CookieService,private messageService:MessageService) {

  }

  getCurrentUser(): User | null{
    return this.userState;
  }

  setCurrentUser(user: User | null){
    this.userState = user;
  }

  getMe():Observable<ApiResponse<User>> {
    return this.http.get<ApiResponse<User>>(`${environment.apiUrl}/users/me`)
  }

  getAccessToken():Observable<ApiResponse<string>> {
    return this.http.post<ApiResponse<string>>(`${environment.apiUrl}/auth/refresh`,{})
  }

  login(data:Login):Observable<ApiResponse<Token>>{
    return this.http.post<ApiResponse<Token>>(`${environment.apiUrl}/auth/login`,data).pipe(
      catchError(handleError)
    );
  }

  register(data:Register):Observable<ApiResponse<boolean>>{
    return this.http.post<ApiResponse<boolean>>(`${environment.apiUrl}/auth/register`,data).pipe(
        catchError(handleError)
    )
  }

  verifyEmail(data:VerifyEmail):Observable<ApiResponse<boolean>>{
    return this.http.post<ApiResponse<boolean>>(`${environment.apiUrl}/auth/verify`,data).pipe(
        catchError(handleError)
    )
  }

  resendVerifyEmail(email:string):Observable<ApiResponse<boolean>>{
    const data = {
      email:email
    }
    return this.http.post<ApiResponse<boolean>>(`${environment.apiUrl}/auth/resend`,data).pipe(
        catchError(handleError)
    )
  }

  logout():Observable<ApiResponse<boolean>>{
      return this.http.post<ApiResponse<boolean>>(`${environment.apiUrl}/auth/logout`,{}).pipe(
        catchError(handleError)
      )
  }
}
