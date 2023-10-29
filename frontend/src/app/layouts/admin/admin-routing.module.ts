import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AdminLayoutComponent} from "./admin-layout/layout/admin-layout.component";
import {DashboardComponent} from "../../features/admin/dashboard/dashboard.component";
import {AdminLayoutModule} from "./admin-layout/admin-layout.module";


const routes: Routes = [
  {
    path:"",
    component:AdminLayoutComponent,
    children:[
      {
        path:"bai-viet",
        loadChildren: () => import("../../features/admin/post/post-admin.module").then(m => m.PostAdminModule)
      },
      {
        path:"nguoi-dung",
        loadChildren: () => import("../../features/admin/user/user-admin.module").then(m => m.UserAdminModule)
      },
      {
        path:"tag",
        loadChildren: () => import("../../features/admin/tag/tag-admin.module").then(m => m.TagAdminModule)
      },
      {
        path:"",
        component:DashboardComponent
      },
    ]
  }
]

@NgModule({
  imports:[AdminLayoutModule,RouterModule.forChild(routes)],
  exports:[RouterModule]
})
export class AdminRoutingModule{}
