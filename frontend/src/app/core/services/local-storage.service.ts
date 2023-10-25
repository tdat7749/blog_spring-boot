import {Injectable} from "@angular/core";

@Injectable({
    providedIn: "root"
})
export class LocalStorageService{
    constructor() {

    }

    get(key:string): string | null{
        const value =  localStorage.getItem(key)
        if(value){
            return value
        }
        return null
    }

    set(key:string,value:string){
        localStorage.setItem(key,value)
    }

    remove(key:string){
        localStorage.removeItem(key)
    }

    clear(){
        localStorage.clear()
    }
}