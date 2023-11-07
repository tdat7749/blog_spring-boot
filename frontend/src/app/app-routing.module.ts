import { NgModule } from '@angular/core';
import { RouterModule, Routes,PreloadAllModules } from '@angular/router';
import {adminGuard} from "./core/guards/admin.guard";
import {NotFoundComponent} from "./shared/components/not-found/not-found.component";

const routes: Routes = [
  {
    path:"admin",
    canActivate:[adminGuard],
    loadChildren:() => import("./layouts/admin/admin.module").then(m => m.AdminModule)
  },
  {
    path:"",
    loadChildren:() => import("./layouts/main/main.module").then(m => m.MainModule)
  },
  {
    path:"khong-tim-thay",
    component:NotFoundComponent
  },
  {
    path:"**",
    redirectTo:"/khong-tim-thay"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{
    preloadingStrategy: PreloadAllModules // ky thuat preloading, load cac bundle o bg
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
