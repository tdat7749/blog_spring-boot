import {NgModule} from "@angular/core";
import {SeriesComponent} from "./series.component";
import {CommonModule} from "@angular/common";
import {SeriesAdminRoutingModule} from "./series-admin-routing.module";
import {ButtonModule} from "primeng/button";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {DialogModule} from "primeng/dialog";
import {InputTextModule} from "primeng/inputtext";
import {PaginatorModule} from "primeng/paginator";
import {SharedModule} from "../../../shared/components/shared.module";
import {TabMenuModule} from "primeng/tabmenu";
import {TagModule} from "primeng/tag";

@NgModule({
    declarations:[
        SeriesComponent
    ],
    imports: [
        CommonModule,
        SeriesAdminRoutingModule,
        ButtonModule,
        ConfirmPopupModule,
        DialogModule,
        InputTextModule,
        PaginatorModule,
        SharedModule,
        TabMenuModule,
        TagModule
    ]
})

export class SeriesAdminModule {}