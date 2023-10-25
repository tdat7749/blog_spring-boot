import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {inject} from "@angular/core";
import {CookieService} from "ngx-cookie-service";
import {AuthService} from "../services/auth.service";
import {map} from "rxjs";
import {LocalStorageService} from "../services/local-storage.service";


export const adminGuard:CanActivateFn = (route:ActivatedRouteSnapshot, state:RouterStateSnapshot) => {
    const router = inject(Router)
    const localStorageService = inject(LocalStorageService)
    const authService = inject(AuthService)
    let flag:boolean = false

    const accessToken = localStorageService.get("accessToken")
    if(!accessToken){
        router.navigate(["/"])
        return false
    }
    const currentUser = authService.getCurrentUser()
    if(currentUser && currentUser.role === "ADMIN"){
        return true
    }

    const subscription =  authService.getMe().pipe(
        map(response => response.data),
    ).subscribe({
        next:(response) =>{
            authService.setCurrentUser(response)
            if(response && response.role === "ADMIN"){
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