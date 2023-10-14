import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {inject} from "@angular/core";
import {CookieService} from "ngx-cookie-service";
import {AuthService} from "../services/auth.service";
import {map} from "rxjs";


export const notAuthGuard:CanActivateFn = (route:ActivatedRouteSnapshot, state:RouterStateSnapshot) => {
    const router = inject(Router)
    const cookieService = inject(CookieService)
    const authService = inject(AuthService)

    const accessToken = cookieService.get("accessToken")
    if(accessToken){
        router.navigate(["/"])
        return false
    }

    const currentUser = authService.getCurrentUser()
    if(currentUser){
        router.navigate(["/"])
        return false
    }

    return true
}