import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthInterceptor} from "./core/interceptors/auth.interceptor";
import {ConfirmationService, MessageService} from "primeng/api";
import { PostComponent } from './features/admin/post/post.component';
import { DashboardComponent } from './features/admin/dashboard/dashboard.component';
import {SharedModule} from "./shared/components/shared.module";
import {ButtonModule} from "primeng/button";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {PaginatorModule} from "primeng/paginator";
import {TagModule} from "primeng/tag";
import {InputTextModule} from "primeng/inputtext";
import {TabMenuModule} from "primeng/tabmenu";
import {DialogModule} from "primeng/dialog";

@NgModule({
  declarations: [
    AppComponent,
    PostComponent,
    DashboardComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    SharedModule,
    ButtonModule,
    ConfirmPopupModule,
    PaginatorModule,
    TagModule,
    InputTextModule,
    TabMenuModule,
    DialogModule,
  ],
  providers: [
    MessageService,
    ConfirmationService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
