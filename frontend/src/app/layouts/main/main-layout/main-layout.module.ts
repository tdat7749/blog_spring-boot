import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import { HeaderComponent } from './header/header.component';
import { MainLayoutComponent } from './layout/main-layout.component';
import { FooterComponent } from './footer/footer.component';
import {ButtonModule} from 'primeng/button'

@NgModule({
  declarations:[
    MainLayoutComponent,
    FooterComponent,
    HeaderComponent,
  ],
  imports:[
    CommonModule,
    ReactiveFormsModule,
    ButtonModule
  ]
})
export class MainLayoutModule{}
