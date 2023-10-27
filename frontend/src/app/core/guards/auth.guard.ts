import {
    ActivatedRouteSnapshot,
    CanActivateChildFn,
    CanActivateFn,
    Router,
    RouterStateSnapshot,
    UrlTree
} from "@angular/router";
import {inject} from "@angular/core";
import {CookieService} from "ngx-cookie-service";
import {AuthService} from "../services/auth.service";
import {catchError, from, map, of, switchMap, tap} from "rxjs";
import {LocalStorageService} from "../services/local-storage.service";


export const authGuard:CanActivateFn = (route:ActivatedRouteSnapshot, state:RouterStateSnapshot) => {
    const router = inject(Router)
    const localStorageService = inject(LocalStorageService)
    const authService = inject(AuthService)
    const accessToken = localStorageService.get("accessToken")
    if(!accessToken){
        router.navigate(["/"])
        return false
    }
    const currentUser = authService.getCurrentUser()
    if(currentUser){
        return true
    }

    return from(authService.getMe()).pipe(
        switchMap((response) => {
            authService.setCurrentUser(response.data)
            return of(true)
        }),
        catchError(() => {
            return of(false)
        })
    )
}

export const authChildGuard:CanActivateChildFn = (route:ActivatedRouteSnapshot, state:RouterStateSnapshot) =>{
    return authGuard(route,state)
}