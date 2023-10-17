import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable} from "rxjs";
import {ApiResponse, PagingResponse, SortBy} from "../types/api-response.type";
import {CreateSeries, Series, SeriesListPost, UpdateSeries} from "../types/series.type";
import {environment} from "../../../environments/environment";
import {handleError} from "../../shared/commons/handle-error-http";

@Injectable({
    providedIn:"root"
})
export class SeriesService{
    constructor(private http:HttpClient) {

    }

    getSeriesById(id:number):Observable<ApiResponse<Series>>{
        return this.http.get<ApiResponse<Series>>(`${environment.apiUrl}/series/${id}`).pipe(
            catchError(handleError)
        )
    }

    getSeriesDetailBySlug(slug:string):Observable<ApiResponse<SeriesListPost>>{
        return this.http.get<ApiResponse<SeriesListPost>>(`${environment.apiUrl}/series/slug/${slug}`).pipe(
            catchError(handleError)
        )
    }

    getAllSeries(pageIndex:number,sortBy:SortBy):Observable<ApiResponse<PagingResponse<Series[]>>>{
        return this.http.get<ApiResponse<PagingResponse<Series[]>>>(`${environment.apiUrl}/series?pageIndex=${pageIndex - 1}&sortBy=${sortBy}`).pipe(
            catchError(handleError)
        )
    }

    createSeries(data:CreateSeries):Observable<ApiResponse<Series>>{
        return this.http.post<ApiResponse<Series>>(`${environment.apiUrl}/series`,data).pipe(
            catchError(handleError)
        )
    }

    updateSeries(data:UpdateSeries,seriesId:number):Observable<ApiResponse<Series>>{
        return this.http.put<ApiResponse<Series>>(`${environment.apiUrl}/series/${seriesId}`,data).pipe(
            catchError(handleError)
        )
    }

    deleteSeries(id:number):Observable<ApiResponse<boolean>>{
        return this.http.delete<ApiResponse<boolean>>(`${environment.apiUrl}/series/${id}`).pipe(
            catchError(handleError)
        )
    }
}