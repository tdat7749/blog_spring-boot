import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {
  BehaviorSubject,
  catchError, concatMap,
  filter,
  finalize,
  mergeMap,
  Observable,
  switchMap,
  take,
  tap,
  throwError
} from "rxjs";
import {CookieService} from "ngx-cookie-service";
import {Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";
import {environment} from "../../../environments/environment";
import {LocalStorageService} from "../services/local-storage.service";


@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor{

  private allowRoute = [
    `${environment.apiUrl}/auth/login`,
    `${environment.apiUrl}/auth/refresh`,
      `${environment.apiUrl}/auth/register`,
      `${environment.apiUrl}/auth/resend`,
      `${environment.apiUrl}/posts/latest`,
      `${environment.apiUrl}/posts/most-view`,
      `${environment.apiUrl}/tags/`

  ]

  private isRetry: boolean = false;
  constructor(private localStorageService: LocalStorageService,private authService: AuthService) {
  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let request = req;
    const token = this.localStorageService.get("accessToken")
    const rfToken = this.localStorageService.get("refreshToken")


    if(rfToken !== null){
      request =  request.clone({
        setHeaders:{
          "RfToken": rfToken
        }
      })
    }

    if((token === null || token === "") || this.allowRoute.includes(request.url)){
      return next.handle(request);
    }

    request = this.addTokenToHeader(request,token)

    return next.handle(request).pipe(
      catchError((error) =>{
        if(error instanceof HttpErrorResponse && error.status === 401 && !this.isRetry){
          return this.handleRefreshToken(request,next)
        }
        return throwError(() => error.error)
      })
    )
  }

  private handleRefreshToken(request: HttpRequest<any>,next: HttpHandler){
    const refreshToken = this.localStorageService.get("refreshToken")
    if(!this.isRetry){
      this.isRetry = true


      if(refreshToken){
        return this.authService.getAccessToken().pipe(
          concatMap((response:any) =>{
            this.isRetry = false
            this.localStorageService.set("accessToken",response.data)
            return next.handle(this.addTokenToHeader(request,response.data))
          }),
          catchError((error:any) =>{
            this.isRetry = false
            this.localStorageService.remove("accessToken")
            this.localStorageService.remove("refreshToken")
            return throwError(() => error);
          })
        )
      }
    }
    return next.handle(request)
  }

  private addTokenToHeader(request:HttpRequest<any>,token?: string){
    let headers = request.headers;

    if(token){
      headers = headers.set("Authorization",`Bearer ${token}`)
    }

    return request.clone({headers})
  }
}
