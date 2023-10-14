import {NgModule} from "@angular/core";
import {RegisterComponent} from "./register.component";
import {RegisterRoutingModule} from "./register-routing.module";
import {ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {ButtonModule} from "primeng/button";
import {InputTextModule} from "primeng/inputtext";

@NgModule({
    declarations:[
        RegisterComponent
    ],
    imports:[
        CommonModule,
        ReactiveFormsModule,
        ButtonModule,
        InputTextModule,
        RegisterRoutingModule
    ]
})

export class RegisterModule {}