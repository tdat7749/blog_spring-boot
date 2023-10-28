import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AdminLayoutComponent} from "./admin-layout/layout/admin-layout.component";
import {DashboardComponent} from "../../features/admin/dashboard/dashboard.component";
import {PostComponent} from "../../features/admin/post/post.component";


const routes: Routes = [
  {
    path:"",
    component:AdminLayoutComponent,
    children:[
      {
        path:"",
        component:DashboardComponent
      },
      {
        path:"bai-viet",
        component:PostComponent
      }
    ]
  }
]

@NgModule({
  imports:[RouterModule.forChild(routes)],
  exports:[RouterModule]
})
export class AdminRoutingModule{}
