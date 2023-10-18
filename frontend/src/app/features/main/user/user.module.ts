import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {UserRoutingModule} from "./user-routing.module";
import { SidebarComponent } from './sidebar/sidebar.component';
import {UserComponent} from "./user.component";
import { UserInformationComponent } from './user-information/user-information.component';
import {AvatarModule} from "primeng/avatar";
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {FileUploadModule} from "primeng/fileupload";
import { ChangePasswordComponent } from './change-password/change-password.component';
import { PostManagementComponent } from './post-management/post-management.component';
import {TableModule} from "primeng/table";
import {MultiSelectModule} from "primeng/multiselect";
import {DropdownModule} from "primeng/dropdown";
import {TagModule} from "primeng/tag";
import {SliderModule} from "primeng/slider";
import {PaginatorModule} from "primeng/paginator";
import {EditorModule} from "primeng/editor";
import { CreatePostComponent } from './create-post/create-post.component';
import { EditPostComponent } from './edit-post/edit-post.component';

@NgModule({
    declarations:[
    UserComponent,
    SidebarComponent,
    UserInformationComponent,
    ChangePasswordComponent,
    PostManagementComponent,
    CreatePostComponent,
    EditPostComponent
  ],
    imports: [
        CommonModule,
        UserRoutingModule,
        AvatarModule,
        ButtonModule,
        DialogModule,
        FormsModule,
        InputTextModule,
        ReactiveFormsModule,
        FileUploadModule,
        TableModule,
        MultiSelectModule,
        DropdownModule,
        TagModule,
        SliderModule,
        PaginatorModule,
        EditorModule
    ]
})

export class UserModule{}