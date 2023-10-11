import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MainRoutingModule} from "./main-routing.module";
import {MainLayoutModule} from "./main-layout/main-layout.module";
import {MessageService} from "primeng/api";
import {MessageModule} from "primeng/message";
import {ToastModule} from "primeng/toast";
import {HttpClientModule} from "@angular/common/http";


@NgModule({
  declarations:[],
  imports:[
    CommonModule,
    MainRoutingModule,
  ]
})
export class MainModule{}
