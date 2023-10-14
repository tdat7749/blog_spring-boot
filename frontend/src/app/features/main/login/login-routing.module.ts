import {NgModule} from "@angular/core";
import {Routes, RouterModule} from "@angular/router";
import {LoginComponent} from "./login.component";
import {notAuthGuard} from "../../../core/guards/not-auth.guard";

const routes:Routes = [
    {
        path: "",
        canActivate:[notAuthGuard],
        component:LoginComponent
    }
]

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class LoginRoutingModule{}