import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {MainLayoutComponent} from "./main-layout/layout/main-layout.component";
import {MainLayoutModule} from "./main-layout/main-layout.module";
import {HomeComponent} from "../../features/main/home/home.component";
import {notAuthGuard} from "../../core/guards/not-auth.guard";
import {authGuard} from "../../core/guards/auth.guard";

const routes: Routes = [
  {
    path:"",
    component: MainLayoutComponent,
    children:[
      {
        path:"bai-viet",
        loadChildren: () => import("../../features/main/post/post.module").then(m => m.PostModule)
      },
      {
        path:"series",
        loadChildren: () => import("../../features/main/series/series.module").then(m => m.SeriesModule)
      },
      {
        path:"tags",
        loadChildren: () => import("../../features/main/tag/tag.module").then(m => m.TagModule)
      },
      {
        path:"tac-gia",
        loadChildren: () => import("../../features/main/author/author.module").then(m => m.AuthorModule)
      },
      {
        path:"nguoi-dung",
        canActivate:[authGuard],
        loadChildren: () => import("../../features/main/user/user.module").then(m => m.UserModule)
      },
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
        path:"lay-lai-mat-khau",
        canActivate:[notAuthGuard],
        loadChildren: () => import("../../features/main/forgot-password/forgot-password.module").then(m => m.ForgotPasswordModule)
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
