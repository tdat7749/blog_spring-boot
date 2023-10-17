import {
  AfterViewInit,
  Component,
  EventEmitter,
  OnInit,
  Output,
  ViewChild,
  ViewEncapsulation,
  ViewRef
} from '@angular/core';
import {AuthService} from "../../../../core/services/auth.service";
import {FileStorageService} from "../../../../core/services/file-storage.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Avatar} from "primeng/avatar";
import {FileBeforeUploadEvent, FileSelectEvent, FileUpload, UploadEvent} from "primeng/fileupload";
import {ChangeInformation, User} from "../../../../core/types/user.type";
import {MessageService} from "primeng/api";
import {concatMap} from "rxjs";
import {UserService} from "../../../../core/services/user.service";
import {noWhiteSpaceValidator} from "../../../../shared/validators/no-white-space.validator";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {SidebarComponent} from "../sidebar/sidebar.component";

@Component({
  selector: 'main-user-information',
  templateUrl: './user-information.component.html',
  styleUrls: ['./user-information.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserInformationComponent implements OnInit{

  isLoading:boolean = false

  changeAvatarFormVisible:boolean = false

  currentUser:User
  avatarValueChange:string | unknown
  fileUploadValue: File | undefined

  changeInformationForm:FormGroup

  @ViewChild("avatar") avatar: Avatar
  @ViewChild("fileUpload") fileUpload: FileUpload
  @ViewChild("userSidebar") userSidebar: SidebarComponent

  @Output() currentPath = new EventEmitter<string>()

  constructor(
      private fileStorageService:FileStorageService,
      private fb:FormBuilder,
      private messageService:MessageService,
      private userService:UserService,
      private _route: ActivatedRoute
  ) {

  }
  ngOnInit() {


    this.currentUser = {
      email:"abc@gmail.com",
      firstName:"a",
      lastName:"b",
      role:"USER",
      avatar:null,
      userName:"a",
      notLocked:true,
      id:1
    }
    this.avatarValueChange = this.currentUser.avatar

    this.changeInformationForm = this.fb.group({
      firstName:[
          this.currentUser.firstName,
          Validators.compose([
              Validators.required,
            Validators.minLength(1),
            Validators.maxLength(60),
              noWhiteSpaceValidator()
          ])
      ],
      lastName:[
          this.currentUser.lastName,
          Validators.compose([
            Validators.required,
            Validators.minLength(1),
            Validators.maxLength(70),
            noWhiteSpaceValidator()
          ])
      ],
      email:[
        {
          value: this.currentUser.email,
          disabled: true
        }
      ],
      role:[
        {
          value: this.currentUser.role,
          disabled: true
        }
      ],
      userName:[
        {
          value: this.currentUser.userName,
          disabled: true
        }
      ]
    })

  }

  onChangeAvatarFormVisible(){
    this.changeAvatarFormVisible = true
  }

  onSelectImage(event: any){
    const file = event.files[0]
    this.avatarValueChange = file?.objectURL.changingThisBreaksApplicationSecurity
    this.fileUploadValue = file
  }

  onUploadImage(){
    if(this.fileUploadValue === null || this.fileUploadValue === undefined){
      this.messageService.add({
        severity: "error",
        detail: "Bạn chưa chọn hình ảnh",
        summary:"Lỗi"
      })
      return;
    }
    this.isLoading = true
    let formData = new FormData()
    formData.set("file",this.fileUploadValue)

    this.fileStorageService.upload(formData).pipe(
        concatMap(response =>{
          return this.userService.changeAvatar(response.data);
        })
    ).subscribe({
      next:(response) =>{
        this.messageService.add({
          severity: "success",
          detail: response.message,
          summary:"Thành công"
        })
        this.currentUser.avatar = response.data
        this.isLoading = false
      },
      error:(error =>{
        this.messageService.add({
          severity: "error",
          detail: error,
          summary:"Lỗi"
        })
        this.isLoading = false
      })
    })
  }
  clearUpload(){
    this.fileUpload.clear()
    this.avatarValueChange = this.currentUser.avatar
  }

  changeInformation(){
    if(this.changeInformationForm.valid){
      const data:ChangeInformation = {
        firstName: this.changeInformationForm.get("firstName")?.value,
        lastName: this.changeInformationForm.get("lastName")?.value
      }
      this.isLoading = true
      this.userService.changeInformation(data).subscribe({
        next:(response) =>{
          this.messageService.add({
            severity: "success",
            detail: response.message,
            summary:"Thành công"
          })
        },
        error:(error) =>{
          this.messageService.add({
            severity: "error",
            detail: error,
            summary:"Lỗi"
          })
        }
      })

    }else{
      this.messageService.add({
        severity: "error",
        detail: "Vui lòng nhập thông tin đúng yêu cầu",
        summary:"Lỗi"
      })
    }
  }
}
