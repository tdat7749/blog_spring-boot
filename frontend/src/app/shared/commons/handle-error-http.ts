import {HttpErrorResponse} from "@angular/common/http";
import {throwError} from "rxjs";

export function handleError(error: HttpErrorResponse){
    if(error.status === 0){
        console.error("An error occurred: ",error)
    }else{
        console.error(
            `Backend returned code ${error}, body was: `, error);
    }
    return throwError(() => {
        if(error?.message){
            return error.message
        }
        return "Có lỗi xảy ra, vui lòng thử lại sau"
    })
}