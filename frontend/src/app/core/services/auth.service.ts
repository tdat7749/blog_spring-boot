import {Injectable} from "@angular/core";
import {User} from "../types/user.type";
import {catchError, Observable, of, throwError} from "rxjs";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ApiResponse} from "../types/api-response.type";
import {environment} from "../../../environments/environment";
import {Login} from "../types/auth.type";
import {Token} from "../types/token.type";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private isLoading:boolean = false;

  user: User | null = {
    userName:"namhoang",
    firstName:"Thien",
    lastName:"Dat",
    role:"ADMIN",
    avatar:"",
    email:"hyperiondat@gmail.com"
  }
  constructor(private http: HttpClient) {
  }

  getCurrentUser(){
    return this.user;
  }

  setCurrentUser(user: User){
    this.user = user;
  }

  getMe():Observable<ApiResponse<User>> {
    return this.http.get<ApiResponse<User>>(`${environment.apiUrl}/auth/me`).pipe(
      catchError((error) =>{
        console.log(error);
        return of(error)
      })
    )
  }

  getAccessToken(refreshToken: string):Observable<ApiResponse<string>> {
    return this.http.get<ApiResponse<string>>(`${environment.apiUrl}/auth/refresh`);
  }

  login(data:Login):Observable<ApiResponse<Token>>{
    return this.http.post<ApiResponse<Token>>(`${environment.apiUrl}/auth/login`,data).pipe(
      catchError((error) =>{
        console.log(error);
        return of(error)
      })
    );
  }

}
