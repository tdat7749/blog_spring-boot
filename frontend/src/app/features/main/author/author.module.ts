import {NgModule} from "@angular/core";
import {AuthorComponent} from "./author.component";
import {CommonModule} from "@angular/common";
import {AuthorRoutingModule} from "./author-routing.module";
import { AuthorInformationComponent } from './author-information/author-information.component';
import {AvatarModule} from "primeng/avatar";
import {SharedModule} from "../../../shared/components/shared.module";
import {ButtonModule} from "primeng/button";
import {PaginatorModule} from "primeng/paginator";
import {InputTextModule} from "primeng/inputtext";

@NgModule({
    declarations:[
        AuthorComponent,
        AuthorInformationComponent
    ],
    imports: [
        CommonModule,
        AuthorRoutingModule,
        AvatarModule,
        SharedModule,
        ButtonModule,
        PaginatorModule,
        InputTextModule
    ],
    exports:[],
})

export class AuthorModule {}