import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {UserComponent} from "./user.component";
import {UserInformationComponent} from "./user-information/user-information.component";
import {authChildGuard} from "../../../core/guards/auth.guard";
import {ChangePasswordComponent} from "./change-password/change-password.component";
import {PostManagementComponent} from "./post-management/post-management.component";
import {CreatePostComponent} from "./create-post/create-post.component";
import {EditPostComponent} from "./edit-post/edit-post.component";
import {SeriesManagementComponent} from "./series-management/series-management.component";
import {CreateSeriesComponent} from "./create-series/create-series.component";
import {EditSeriesComponent} from "./edit-series/edit-series.component";

const routes:Routes = [
    {
        path:"",
        component:UserComponent,
        children:[
            {
                path:"thong-tin",
                component:UserInformationComponent,
            },
            {
                path:"doi-mat-khau",
                component:ChangePasswordComponent
            },
            {
                path:"quan-ly-bai-viet",
                component:PostManagementComponent,
            },
            {
                path:"quan-ly-bai-viet/tao-bai-viet",
                component:CreatePostComponent
            },
            {
                path:"quan-ly-bai-viet/sua-bai-viet/:slug",
                component:EditPostComponent
            },
            {
                path:"quan-ly-series",
                component:SeriesManagementComponent,
            },
            {
                path:"quan-ly-series/tao-series",
                component:CreateSeriesComponent,
            },
            {
                path:"quan-ly-series/sua-series/:slug",
                component:EditSeriesComponent,
            }
        ]
    }
]

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class UserRoutingModule{}