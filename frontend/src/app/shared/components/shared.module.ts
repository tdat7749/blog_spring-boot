import {NgModule} from "@angular/core";
import {ListPostCardComponent} from "./list-post-card/list-post-card.component";
import {PostCardComponent} from "./post-card/post-card.component";
import {TagModule} from "primeng/tag";
import {RouterLink} from "@angular/router";
import {CommonModule} from "@angular/common";
import { LoadingComponent } from './loading/loading.component';
import {ProgressSpinnerModule} from "primeng/progressspinner";
import { ListSeriesCardComponent } from './list-series-card/list-series-card.component';
import { SeriesCardComponent } from './series-card/series-card.component';
import { TagCardComponent } from './tag-card/tag-card.component';
import { ListTagCardComponent } from './list-tag-card/list-tag-card.component';
import {AvatarModule} from "primeng/avatar";
import { NotificationItemComponent } from './notification-item/notification-item.component';
import { NotFoundComponent } from './not-found/not-found.component';
import {ButtonModule} from "primeng/button";

@NgModule({
    declarations:[
        ListPostCardComponent,
        PostCardComponent,
        LoadingComponent,
        ListSeriesCardComponent,
        SeriesCardComponent,
        TagCardComponent,
        ListTagCardComponent,
        NotificationItemComponent,
        NotFoundComponent
    ],
    imports: [
        CommonModule,
        TagModule,
        RouterLink,
        ProgressSpinnerModule,
        AvatarModule,
        ButtonModule
    ],
    exports: [
        ListPostCardComponent,
        LoadingComponent,
        ListSeriesCardComponent,
        ListTagCardComponent,
        NotificationItemComponent
    ]
})
export class SharedModule{}