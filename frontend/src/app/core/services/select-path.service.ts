import {Injectable} from "@angular/core";
import {BehaviorSubject} from "rxjs";

@Injectable({
    providedIn: "root"
})
export class SelectPathService {

    path$:BehaviorSubject<string> = new BehaviorSubject<string>("")

}
