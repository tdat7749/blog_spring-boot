import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {
  BehaviorSubject,
  catchError,
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


@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor{

  private allowRoute = [
    `${environment.apiUrl}/auth/login`,
  ]

  private isRetry: boolean = false;
  private refreshToken$: BehaviorSubject<any> = new BehaviorSubject<any>(null)
  constructor(private cookieService: CookieService,private authService: AuthService) {
  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let request = req;
    const token = this.cookieService.get("accessToken")
    const rfToken = this.cookieService.get("refreshToken")
    if((token === null || token === "") || this.allowRoute.includes(request.url)){
      return next.handle(req);
    }

    request = this.addTokenToHeader(request,token,rfToken)

    return next.handle(request).pipe(
      catchError((error) =>{
        if(error instanceof HttpErrorResponse && error.status === 401){
          return this.handleRefreshToken(request,next)
        }

        return throwError(() => error.error)
      })
    )
  }

  private handleRefreshToken(request: HttpRequest<any>,next: HttpHandler){
    if(!this.isRetry){
      this.isRetry = true
      this.refreshToken$.next(null)

      const refreshToken = this.cookieService.get("refreshToken")

      if(refreshToken){
        return this.authService.getAccessToken(refreshToken).pipe(
          switchMap((response:any) =>{
            this.isRetry = false;
            this.cookieService.set("accessToken",response.data.accessToken)
            this.refreshToken$.next(response.data.accessToken)

            return next.handle(this.addTokenToHeader(request,response.data.accessToken,refreshToken))
          }),
          catchError((error:HttpErrorResponse) =>{
            this.isRetry = false;
            this.cookieService.delete("accessToken")
            this.cookieService.delete("refreshToken")
            return throwError(() => error.error);
          })
        )
      }
    }

    return this.refreshToken$.pipe(
      filter(token => token !== null),
      take(1),
      switchMap((token) => next.handle(this.addTokenToHeader(request,token)))
    )
  }

  private addTokenToHeader(request:HttpRequest<any>,token: string,refreshToken?:string){
    let headers = request.headers;

    headers = headers.set("Authorization",`Bearer ${token}`)

    if(refreshToken){
      headers = headers.set("rfToken",refreshToken)
      console.log(refreshToken)
    }

    return request.clone({headers})
  }
}
