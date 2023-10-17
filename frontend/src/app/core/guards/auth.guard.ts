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
import {map, tap} from "rxjs";


export const authGuard:CanActivateFn = (route:ActivatedRouteSnapshot, state:RouterStateSnapshot) => {
    const router = inject(Router)
    const cookieService = inject(CookieService)
    const authService = inject(AuthService)
    let flag:boolean = false
    const accessToken = cookieService.get("accessToken")
    if(!accessToken){
        router.navigate(["/"])
        return false
    }
    const currentUser = authService.getCurrentUser()
    if(currentUser){
        return true
    }

    authService.getMe().pipe(
        map(response => response.data),
    ).subscribe({
        next:(response) => {
            console.log(response)
            authService.setCurrentUser(response)
            if(response !== null){
                flag = true
            }
        },
        error:() =>{
            flag = false
        }
    })

    if(!flag){
        router.navigate(["/"])
        return false
    }
    return true
}

export const authChildGuard:CanActivateChildFn = (route:ActivatedRouteSnapshot, state:RouterStateSnapshot) =>{
    return authGuard(route,state)
}