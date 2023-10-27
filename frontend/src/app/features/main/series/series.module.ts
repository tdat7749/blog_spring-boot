import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import { SeriesRoutingModule } from "./series-routing.module";
import {SeriesDetailComponent} from "./series-detail/series-detail.component";
import {SeriesComponent} from "./series.component";
import {InputTextModule} from "primeng/inputtext";
import {PaginatorModule} from "primeng/paginator";
import {SharedModule} from "../../../shared/components/shared.module";
import {AvatarModule} from "primeng/avatar";
import {ButtonModule} from "primeng/button";
import {TagModule} from "primeng/tag";

@NgModule({
   declarations:[
        SeriesComponent,
        SeriesDetailComponent
   ],
    imports: [
        CommonModule,
        SeriesRoutingModule,
        PaginatorModule,
        InputTextModule,
        SharedModule,
        AvatarModule,
        ButtonModule,
        TagModule
    ],
})
export class SeriesModule{

}