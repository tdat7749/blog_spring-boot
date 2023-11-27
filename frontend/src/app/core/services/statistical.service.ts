import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ApiResponse} from "../types/api-response.type";
import {Statistical} from "../types/statistical.type";
import {environment} from "../../../environments/environment";
import {handleError} from "../../shared/commons/handle-error-http";
import {catchError, Observable} from "rxjs";

@Injectable({
    providedIn: "root"
})
export class StatisticalService{
    constructor(private http:HttpClient) {

    }

    getDashboard():Observable<ApiResponse<Statistical>>{
        return this.http.get<ApiResponse<Statistical>>(`${environment.apiUrl}/statistical/dashboard`).pipe(
            catchError(handleError)
        )
    }
}