import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AuthorComponent} from "./author.component";
import {AuthorPostComponent} from "./author-post/author-post.component";
import {AuthorFollowerComponent} from "./author-follower/author-follower.component";
import {AuthorFollowingComponent} from "./author-following/author-following.component";

const routes:Routes = [
    {
        path:":userName",
        component:AuthorComponent,
        children:[
            {
                path:"",
                component:AuthorPostComponent,
                data:{

                }
            },
            {
                path:"dang-theo-doi",
                component:AuthorFollowerComponent
            },
            {
                path:"nguoi-theo-doi",
                component:AuthorFollowingComponent
            }
        ]
    }
]

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class AuthorRoutingModule{}