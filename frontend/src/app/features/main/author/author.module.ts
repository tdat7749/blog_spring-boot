import {NgModule} from "@angular/core";
import {AuthorComponent} from "./author.component";
import {CommonModule} from "@angular/common";
import {AuthorRoutingModule} from "./author-routing.module";
import { AuthorInformationComponent } from './author-information/author-information.component';
import {AvatarModule} from "primeng/avatar";
import {SharedModule} from "../../../shared/components/shared.module";
import {ButtonModule} from "primeng/button";
import {PaginatorModule} from "primeng/paginator";
import {InputTextModule} from "primeng/inputtext";
import { AuthorPostComponent } from './author-post/author-post.component';
import { AuthorFollowerComponent } from './author-follower/author-follower.component';
import { AuthorFollowingComponent } from './author-following/author-following.component';
import {TabMenuModule} from "primeng/tabmenu";

@NgModule({
    declarations:[
        AuthorComponent,
        AuthorInformationComponent,
        AuthorPostComponent,
        AuthorFollowerComponent,
        AuthorFollowingComponent
    ],
    imports: [
        CommonModule,
        AuthorRoutingModule,
        AvatarModule,
        SharedModule,
        ButtonModule,
        PaginatorModule,
        InputTextModule,
        TabMenuModule
    ],
    exports:[],
})

export class AuthorModule {}