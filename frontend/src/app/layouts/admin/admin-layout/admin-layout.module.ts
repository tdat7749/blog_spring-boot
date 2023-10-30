import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { AdminLayoutComponent } from './layout/admin-layout.component';
import {ReactiveFormsModule} from "@angular/forms";
import { AdminSidebarComponent } from './admin-sidebar/admin-sidebar.component';
import {PanelMenuModule} from "primeng/panelmenu";
import {ToastModule} from "primeng/toast";
import {AvatarModule} from "primeng/avatar";
import {BadgeModule} from "primeng/badge";
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {DividerModule} from "primeng/divider";
import {OverlayPanelModule} from "primeng/overlaypanel";
import {SharedModule} from "../../../shared/components/shared.module";



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
        ToastModule,
        AvatarModule,
        BadgeModule,
        ButtonModule,
        DialogModule,
        DividerModule,
        OverlayPanelModule,
        SharedModule
    ]
})
export class AdminLayoutModule { }
