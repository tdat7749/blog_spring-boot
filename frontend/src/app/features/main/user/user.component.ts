import {Component, OnInit, ViewChild, ViewEncapsulation} from "@angular/core";
import {UserInformationComponent} from "./user-information/user-information.component";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector:"main-user",
    templateUrl:"./user.component.html",
    styleUrls:["./user.component.css"]
})

export class UserComponent implements OnInit{
    constructor(private _route:ActivatedRoute) {
    }

    ngOnInit() {
    }
}