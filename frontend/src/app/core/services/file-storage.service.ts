import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable} from "rxjs";
import {ApiResponse} from "../types/api-response.type";
import {environment} from "../../../environments/environment";
import {handleError} from "../../shared/commons/handle-error-http";

@Injectable({
    providedIn: "root"
})

export class FileStorageService{
    constructor(private http:HttpClient) {

    }

    upload(file: FormData):Observable<ApiResponse<string>>{
        return this.http.post<ApiResponse<string>>(`${environment.apiUrl}/filestorage/upload`,file).pipe(
            catchError(handleError)
        )
    }
}