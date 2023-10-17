import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {UserComponent} from "./user.component";
import {UserInformationComponent} from "./user-information/user-information.component";
import {authChildGuard} from "../../../core/guards/auth.guard";
import {ChangePasswordComponent} from "./change-password/change-password.component";
import {PostManagementComponent} from "./post-management/post-management.component";

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
                component:PostManagementComponent
            }
        ]
    }
]

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class UserRoutingModule{}