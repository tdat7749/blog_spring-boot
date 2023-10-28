import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { AdminLayoutComponent } from './layout/admin-layout.component';
import {ReactiveFormsModule} from "@angular/forms";
import { AdminSidebarComponent } from './admin-sidebar/admin-sidebar.component';
import {PanelMenuModule} from "primeng/panelmenu";
import {ToastModule} from "primeng/toast";



@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    AdminLayoutComponent,
    AdminSidebarComponent
  ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        PanelMenuModule,
        ToastModule
    ]
})
export class AdminLayoutModule { }
