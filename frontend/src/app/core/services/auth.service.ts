import {inject, Injectable} from "@angular/core";
import {User} from "../types/user.type";
import {BehaviorSubject, catchError, Observable, of, throwError} from "rxjs";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ApiResponse} from "../types/api-response.type";
import {environment} from "../../../environments/environment";
import {Login} from "../types/auth.type";
import {Token} from "../types/token.type";
import {CookieService} from "ngx-cookie-service";
import {MessageService} from "primeng/api";

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  userState: User | null = null;

  userState$: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null)
  constructor(private http: HttpClient,private cookieService: CookieService,private messageService:MessageService) {

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

  getAccessToken(refreshToken: string):Observable<ApiResponse<string>> {
    return this.http.post<ApiResponse<string>>(`${environment.apiUrl}/auth/refresh`,{})
  }

  login(data:Login):Observable<ApiResponse<Token>>{
    return this.http.post<ApiResponse<Token>>(`${environment.apiUrl}/auth/login`,data).pipe(
      catchError(this.handleError)
    );
  }

  logout():void {
    this.cookieService.delete("refreshToken")
    this.cookieService.delete("accessToken")
    this.setCurrentUser(null)
    this.userState$.next(null)
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
