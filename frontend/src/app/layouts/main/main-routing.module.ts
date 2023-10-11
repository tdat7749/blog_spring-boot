import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {MainLayoutComponent} from "./main-layout/layout/main-layout.component";
import {PostComponent} from "../../features/main/post/post.component";
import {MainLayoutModule} from "./main-layout/main-layout.module";

const routes: Routes = [
  {
    path:"",
    component: MainLayoutComponent,
    children:[
      {
        path:"bai-viet",
        component:PostComponent
      }
    ]
  }
]

@NgModule({
  imports:[MainLayoutModule,RouterModule.forChild(routes)],
  exports:[RouterModule]
})

export class MainRoutingModule{}
