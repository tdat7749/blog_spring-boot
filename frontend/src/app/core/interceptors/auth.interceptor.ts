import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {BehaviorSubject, catchError, filter, mergeMap, Observable, switchMap, take, throwError} from "rxjs";
import {CookieService} from "ngx-cookie-service";
import {Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";


@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor{

  private isRetry: boolean = false;
  private refreshToken$: BehaviorSubject<any> = new BehaviorSubject<any>(null)
  constructor(private cookieService: CookieService,private authService: AuthService) {
  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let request = req;
    const token = this.cookieService.get("accessToken")
    if(token === null || token === ""){
      return next.handle(req);
    }

    request = this.addTokenToHeader(request,token)

    return next.handle(request).pipe(
      catchError((error:any) =>{
        if(error instanceof HttpErrorResponse && error.status === 401){
          return this.handleRefreshToken(request,next)
        }

        return throwError(error)
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

            return next.handle(this.addTokenToHeader(request,response.data.accessToken))
          }),
          catchError((error) =>{
            this.isRetry = false;

            return throwError(error);
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

  private addTokenToHeader(request:HttpRequest<any>,token: string){
    return request.clone({
      headers: request.headers.set("Authorization",`Bearer ${token}`)
    })
  }
}
