import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {inject} from "@angular/core";
import {CookieService} from "ngx-cookie-service";
import {AuthService} from "../services/auth.service";
import {map} from "rxjs";


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

    const subscription =  authService.getMe().pipe(
        map(response => response.data),
    ).subscribe({
        next:(response) => {
            authService.setCurrentUser(response)
            if(response){
                flag = true
            }
        }
    })

    if(!flag){
        router.navigate(["/"])
        subscription.unsubscribe()
        return false
    }
    subscription.unsubscribe()
    return true
}