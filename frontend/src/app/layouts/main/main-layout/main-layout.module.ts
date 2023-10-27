import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import { HeaderComponent } from './header/header.component';
import { MainLayoutComponent } from './layout/main-layout.component';
import { FooterComponent } from './footer/footer.component';
import {ButtonModule} from 'primeng/button'
import {InputTextModule} from "primeng/inputtext";
import {ToastModule} from "primeng/toast";
import {DividerModule} from "primeng/divider";
import {RouterLink, RouterOutlet} from "@angular/router";
import { SideBarComponent } from './header/side-bar/side-bar.component';
import { SidebarModule } from 'primeng/sidebar';
import {AvatarModule} from "primeng/avatar";
import {OverlayPanelModule} from "primeng/overlaypanel";
import {HomeComponent} from "../../../features/main/home/home.component";
import {PostModule} from "../../../features/main/post/post.module";
import {SharedModule} from "../../../shared/components/shared.module";
import {TagModule} from "primeng/tag";
import {BadgeModule} from "primeng/badge";
import {DialogModule} from "primeng/dialog";

@NgModule({
  declarations:[
    MainLayoutComponent,
    FooterComponent,
    HeaderComponent,
    SideBarComponent,
    HomeComponent

  ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        ButtonModule,
        InputTextModule,
        ToastModule,
        DividerModule,
        SidebarModule,
        RouterLink,
        AvatarModule,
        OverlayPanelModule,
        RouterOutlet,
        SharedModule,
        TagModule,
        BadgeModule,
        DialogModule,
    ],

})
export class MainLayoutModule{}
