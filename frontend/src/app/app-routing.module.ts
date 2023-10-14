import { NgModule } from '@angular/core';
import { RouterModule, Routes,PreloadAllModules } from '@angular/router';
import {adminGuard} from "./core/guards/admin.guard";

const routes: Routes = [

  {
    path:"admin",
    canActivate:[adminGuard],
    loadChildren:() => import("./layouts/admin/admin.module").then(m => m.AdminModule)
  },
  {
    path:"",
    loadChildren:() => import("./layouts/main/main.module").then(m => m.MainModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{
    preloadingStrategy: PreloadAllModules // ky thuat preloading, load cac bundle o bg
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
