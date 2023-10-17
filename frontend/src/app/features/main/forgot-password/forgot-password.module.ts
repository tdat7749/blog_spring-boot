import {NgModule} from "@angular/core";
import {ForgotPasswordComponent} from "./forgot-password.component";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {ForgotPasswordRoutingModule} from "./forgot-password-routing.module";
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {InputTextModule} from "primeng/inputtext";

@NgModule({
    declarations:[
        ForgotPasswordComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        ForgotPasswordRoutingModule,
        ButtonModule,
        DialogModule,
        InputTextModule
    ]
})

export class ForgotPasswordModule{}