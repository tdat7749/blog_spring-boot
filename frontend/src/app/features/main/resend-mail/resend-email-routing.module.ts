import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {ResendEmailComponent} from "./resend-email.component";

const routes:Routes = [
    {
        path:"",
        component:ResendEmailComponent
    }
]

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class ResendEmailRoutingModule{}