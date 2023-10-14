import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {MainLayoutComponent} from "./main-layout/layout/main-layout.component";
import {MainLayoutModule} from "./main-layout/main-layout.module";
import {HomeComponent} from "../../features/main/home/home.component";
import {notAuthGuard} from "../../core/guards/not-auth.guard";

const routes: Routes = [
  {
    path:"",
    component: MainLayoutComponent,
    children:[
      {
        path:"dang-nhap",
        canActivate:[notAuthGuard],
        loadChildren: () => import("../../features/main/login/login.module").then(m => m.LoginModule)
      },
      {
        path:"dang-ky",
        canActivate:[notAuthGuard],
        loadChildren: () => import("../../features/main/register/register.module").then(m => m.RegisterModule)
      },
      {
        path:"xac-thuc",
        canActivate:[notAuthGuard],
        loadChildren: () => import("../../features/main/verify-email/verify-email.module").then(m => m.VerifyEmailModule)
      },
      {
        path:"",
        component:HomeComponent
      }
    ]
  }
]

@NgModule({
  imports:[MainLayoutModule,RouterModule.forChild(routes)],
  exports:[RouterModule]
})

export class MainRoutingModule{}
