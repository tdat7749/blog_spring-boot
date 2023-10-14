import {NgModule} from "@angular/core";
import {VerifyEmailComponent} from "./verify-emai.component";
import {CommonModule} from "@angular/common";
import {VerifyEmailRoutingModule} from "./verify-email-routing.module";
import {ButtonModule} from "primeng/button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {ProgressSpinnerModule} from "primeng/progressspinner";

@NgModule({
    declarations:[
        VerifyEmailComponent
    ],
    imports: [
        CommonModule,
        VerifyEmailRoutingModule,
        ButtonModule,
        FormsModule,
        InputTextModule,
        ReactiveFormsModule,
        ProgressSpinnerModule
    ]
})

export class VerifyEmailModule{}