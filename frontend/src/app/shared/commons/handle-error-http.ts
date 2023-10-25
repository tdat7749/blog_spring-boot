import {HttpErrorResponse} from "@angular/common/http";
import {throwError} from "rxjs";

export function handleError(error: any){
    console.log(typeof error)
    console.log(error)
    if(error?.status === 0){
        return throwError(() => "Có lỗi xảy ra, vui lòng thử lại sau")
    }

    return throwError(() => {
        if(error?.error?.message){
            return error.error.message
        }
        if(error?.message){
            return error.message
        }
        return "Có lỗi xảy ra, vui lòng thử lại sau"
    })
}