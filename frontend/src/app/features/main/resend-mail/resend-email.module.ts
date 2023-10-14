import {NgModule} from "@angular/core";
import {ResendEmailComponent} from "./resend-email.component";
import {CommonModule} from "@angular/common";
import {ResendEmailRoutingModule} from "./resend-email-routing.module";

@NgModule({
    declarations:[
        ResendEmailComponent
    ],
    imports:[
        CommonModule,
        ResendEmailRoutingModule
    ]
})

export class ResendEmailModule{}