import {NgModule} from "@angular/core";
import {TagComponent} from "./tag.component";
import {TagDetailComponent} from "./tag-detail/tag-detail.component";
import {CommonModule} from "@angular/common";
import {TagRoutingModule} from "./tag-routing.module";
import {InputTextModule} from "primeng/inputtext";
import {PaginatorModule} from "primeng/paginator";
import {SharedModule} from "../../../shared/components/shared.module";
import {AvatarModule} from "primeng/avatar";

@NgModule({
    declarations:[
        TagComponent,
        TagDetailComponent
    ],
    imports: [
        CommonModule,
        TagRoutingModule,
        InputTextModule,
        PaginatorModule,
        SharedModule,
        AvatarModule
    ]
})

export class TagModule{}