import {NgModule} from "@angular/core";
import {ListPostCardComponent} from "./list-post-card/list-post-card.component";
import {PostCardComponent} from "./post-card/post-card.component";
import {TagModule} from "primeng/tag";
import {RouterLink} from "@angular/router";
import {CommonModule} from "@angular/common";

@NgModule({
    declarations:[
        ListPostCardComponent,
        PostCardComponent
    ],
    imports: [
        CommonModule,
        TagModule,
        RouterLink
    ],
    exports: [
        ListPostCardComponent
    ]
})
export class SharedModule{}