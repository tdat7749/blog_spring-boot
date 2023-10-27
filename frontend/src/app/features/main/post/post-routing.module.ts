import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {PostDetailComponent} from "./post-detail/post-detail.component";
import {PostComponent} from "./post-component";


const routes:Routes = [
    {
        path:"",
        component:PostComponent
    },
    {
        path:":slug",
        component:PostDetailComponent
    }
]

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class PostRoutingModule{}