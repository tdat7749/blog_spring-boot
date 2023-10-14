import {NgModule} from "@angular/core";
import {ListPostCardComponent} from "./list-post-card/list-post-card.component";
import {PostCardComponent} from "./post-card/post-card.component";
import {PostCardInfoComponent} from "./post-card/post-card-info/post-card-info.component";
import {CommonModule} from "@angular/common";
import {AvatarModule} from "primeng/avatar";

@NgModule({
    declarations: [
        ListPostCardComponent,
        PostCardComponent,
        PostCardInfoComponent
    ],
    exports: [
        ListPostCardComponent
    ],
    imports: [
        CommonModule,
        AvatarModule
    ]
})

export class PostModule{}