import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {inject} from "@angular/core";
import {CookieService} from "ngx-cookie-service";
import {AuthService} from "../services/auth.service";


export const adminGuard:CanActivateFn = (route:ActivatedRouteSnapshot, state:RouterStateSnapshot) => {
    const router = inject(Router)
    const cookieService = inject(CookieService)
    const authService = inject(AuthService)

    const currentUser = authService.getCurrentUser()
    const accessToken = cookieService.get("accessToken")

    if(currentUser && currentUser.role === "ADMIN" && accessToken){
        return true
    }else{
        router.navigate(["/"])
        return false
    }
}