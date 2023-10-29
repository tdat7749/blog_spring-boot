import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {UserRoutingModule} from "../../main/user/user-routing.module";
import {UserComponent} from "./user.component";
import {TabMenuModule} from "primeng/tabmenu";
import {SharedModule} from "../../../shared/components/shared.module";
import {ButtonModule} from "primeng/button";
import {TagModule} from "primeng/tag";
import {PaginatorModule} from "primeng/paginator";
import {UserAdminRoutingModule} from "./user-admin-routing.module";
import {InputTextModule} from "primeng/inputtext";
import {ConfirmPopupModule} from "primeng/confirmpopup";

@NgModule({
    declarations:[
        UserComponent
    ],
    imports: [
        CommonModule,
        UserAdminRoutingModule,
        TabMenuModule,
        SharedModule,
        ButtonModule,
        TagModule,
        PaginatorModule,
        InputTextModule,
        ConfirmPopupModule
    ]
})

export class UserAdminModule {}