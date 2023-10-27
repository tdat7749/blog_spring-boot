import {Injectable} from "@angular/core";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
    providedIn:"root"
})

export class LoadingService{
    private _isLoading$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false)

    get isLoading$():Observable<boolean>{
        return this._isLoading$.asObservable()
    }

    public startLoading(){
        this._isLoading$.next(true)
    }

    public stopLoading(){
        this._isLoading$.next(false)
    }


}