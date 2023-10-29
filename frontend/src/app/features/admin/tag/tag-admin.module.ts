import {NgModule} from "@angular/core";
import {TagComponent} from "./tag.component";
import {CommonModule} from "@angular/common";
import {TagAdminRoutingModule} from "./tag-admin-routing.module";
import {ButtonModule} from "primeng/button";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {InputTextModule} from "primeng/inputtext";
import {PaginatorModule} from "primeng/paginator";
import {SharedModule} from "../../../shared/components/shared.module";
import {TabMenuModule} from "primeng/tabmenu";
import {TagModule} from "primeng/tag";
import {DialogModule} from "primeng/dialog";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
    declarations:[
        TagComponent
    ],
    imports: [
        CommonModule,
        TagAdminRoutingModule,
        ButtonModule,
        ConfirmPopupModule,
        InputTextModule,
        PaginatorModule,
        SharedModule,
        TabMenuModule,
        TagModule,
        DialogModule,
        ReactiveFormsModule
    ]
})

export class TagAdminModule {}