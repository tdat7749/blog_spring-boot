import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {PostAdminRoutingModule} from "./post-admin-routing.module";
import {PostComponent} from "./post.component";
import {TagModule} from "primeng/tag";
import {ButtonModule} from "primeng/button";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {PaginatorModule} from "primeng/paginator";
import {DialogModule} from "primeng/dialog";
import {SharedModule} from "../../../shared/components/shared.module";
import {TabMenuModule} from "primeng/tabmenu";
import {InputTextModule} from "primeng/inputtext";

@NgModule({
    declarations:[
        PostComponent
    ],
    imports: [
        CommonModule,
        PostAdminRoutingModule,
        TagModule,
        ButtonModule,
        ConfirmPopupModule,
        PaginatorModule,
        DialogModule,
        SharedModule,
        TabMenuModule,
        InputTextModule
    ]
})

export class PostAdminModule{}