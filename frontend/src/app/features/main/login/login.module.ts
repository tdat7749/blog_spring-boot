import {NgModule} from "@angular/core";
import {LoginComponent} from "./login.component";
import {ReactiveFormsModule} from "@angular/forms";
import {ButtonModule} from "primeng/button";
import {InputTextModule} from "primeng/inputtext";
import {LoginRoutingModule} from "./login-routing.module";
import {CommonModule} from "@angular/common";

@NgModule({
    declarations:[
        LoginComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        ButtonModule,
        InputTextModule,
        LoginRoutingModule,
    ]
})

export class LoginModule{}