import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AuthorComponent} from "./author.component";

const routes:Routes = [
    {
        path:":userName",
        component:AuthorComponent
    }
]

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class AuthorRoutingModule{}