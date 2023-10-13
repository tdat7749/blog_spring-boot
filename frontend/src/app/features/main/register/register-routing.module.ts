import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {RegisterComponent} from "./register.component";
import {notAuthGuard} from "../../../core/guards/not-auth.guard";

const routes:Routes = [
    {
        path:"",
        canActivate:[notAuthGuard],
        component:RegisterComponent
    }
]

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class RegisterRoutingModule{}