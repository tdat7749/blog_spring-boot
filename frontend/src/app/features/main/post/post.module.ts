import {NgModule} from "@angular/core";
import {ListPostCardComponent} from "../../../shared/components/list-post-card/list-post-card.component";
import {PostCardComponent} from "../../../shared/components/post-card/post-card.component";
import {CommonModule} from "@angular/common";
import {AvatarModule} from "primeng/avatar";
import {TagModule} from "primeng/tag";
import {RouterLink} from "@angular/router";
import { PostDetailComponent } from './post-detail/post-detail.component';
import {PostRoutingModule} from "./post-routing.module";
import { CommentComponent } from './post-detail/comment/comment.component';
import {ButtonModule} from "primeng/button";
import {InputTextModule} from "primeng/inputtext";
import {ReactiveFormsModule} from "@angular/forms";
import {PaginatorModule} from "primeng/paginator";
import {PostComponent} from "./post-component";

@NgModule({
    declarations: [
        PostDetailComponent,
        CommentComponent,
        PostComponent
    ],
    imports: [
        CommonModule,
        AvatarModule,
        TagModule,
        RouterLink,
        ButtonModule,
        InputTextModule,
        ReactiveFormsModule,
        PaginatorModule,
        PostRoutingModule
    ]
})

export class PostModule{}