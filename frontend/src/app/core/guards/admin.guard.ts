import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from "@angular/router";
import {inject} from "@angular/core";
import {AuthService} from "../services/auth.service";
import {catchError, from, of, switchMap} from "rxjs";
import {LocalStorageService} from "../services/local-storage.service";


export const adminGuard:CanActivateFn = (route:ActivatedRouteSnapshot, state:RouterStateSnapshot) => {
    const router = inject(Router)
    const localStorageService = inject(LocalStorageService)
    const authService = inject(AuthService)

    const accessToken = localStorageService.get("accessToken")
    if(!accessToken){
        router.navigate(["/"])
        return false
    }
    const currentUser = authService.getCurrentUser()
    if(currentUser && currentUser.role === "ADMIN"){
        return true
    }

    return from(authService.getMe()).pipe(
        switchMap((response) => {
            authService.setCurrentUser(response.data)
            if(response.data && response.data.role === "ADMIN"){
                return of(true)
            }else{
                return of(false)
            }
        }),
        catchError(() => {
            return of(false)
        })
    )
}