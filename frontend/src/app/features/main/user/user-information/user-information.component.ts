import {
  Component,
  EventEmitter, OnDestroy,
  OnInit,
  Output,
  ViewChild,
  ViewEncapsulation,
} from '@angular/core';
import {AuthService} from "../../../../core/services/auth.service";
import {FileStorageService} from "../../../../core/services/file-storage.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Avatar} from "primeng/avatar";
import {FileUpload} from "primeng/fileupload";
import {ChangeInformation, User} from "../../../../core/types/user.type";
import {MessageService} from "primeng/api";
import {concatMap, Subject, takeUntil} from "rxjs";
import {UserService} from "../../../../core/services/user.service";
import {noWhiteSpaceValidator} from "../../../../shared/validators/no-white-space.validator";
import {SidebarComponent} from "../sidebar/sidebar.component";
import {MAX_FILE, MIME_TYPES} from "../../../../shared/commons/shared";
import {LoadingService} from "../../../../core/services/loading.service";
import {Router} from "@angular/router";

@Component({
  selector: 'main-user-information',
  templateUrl: './user-information.component.html',
  styleUrls: ['./user-information.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserInformationComponent implements OnInit,OnDestroy{

  isLoading:boolean = false

  changeAvatarFormVisible:boolean = false

  currentUser:User
  avatarValueChange:string | unknown
  fileUploadValue: File | undefined

  changeInformationForm:FormGroup

  @ViewChild("avatar") avatar: Avatar
  @ViewChild("fileUpload") fileUpload: FileUpload
  @ViewChild("userSidebar") userSidebar: SidebarComponent

  destroy$ = new Subject<void>()

  @Output() currentPath = new EventEmitter<string>()

  constructor(
      private fileStorageService:FileStorageService,
      private fb:FormBuilder,
      private messageService:MessageService,
      private userService:UserService,
      private authService:AuthService,
      public loadingService:LoadingService,
      private router:Router
  ) {

  }
  ngOnInit() {
    this.changeInformationForm = this.fb.group({
      firstName:[
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(60),
          noWhiteSpaceValidator()
        ])
      ],
      lastName:[
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(70),
          noWhiteSpaceValidator()
        ])
      ],
      summary:[
        '',
        Validators.compose([
          Validators.required,
          Validators.maxLength(700),
          noWhiteSpaceValidator()
        ])
      ],
      email:[
        {
          value: '',
          disabled: true
        }
      ],
      role:[
        {
          value: '',
          disabled: true
        }
      ],
      userName:[
        {
          value: '',
          disabled: true
        }
      ]
    })

    this.loadingService.startLoading()

    this.authService.getMe()
        .pipe(
            takeUntil(this.destroy$)
        ).subscribe({
      next:(response) => {
        this.currentUser = response.data
        this.loadingService.stopLoading()
        this.avatarValueChange = this.currentUser.avatar

        this.changeInformationForm.get("summary")?.setValue(response.data.summary)
        this.changeInformationForm.get("firstName")?.setValue(response.data.firstName)
        this.changeInformationForm.get("lastName")?.setValue(response.data.lastName)
        this.changeInformationForm.get("email")?.setValue(response.data.email)
        this.changeInformationForm.get("role")?.setValue(response.data.role)
        this.changeInformationForm.get("userName")?.setValue(response.data.userName)
      },
      error:(error) =>{
        this.loadingService.stopLoading()
        this.messageService.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.router.navigate(["/"])
      }
    })
  }

  onChangeAvatarFormVisible(){
    this.changeAvatarFormVisible = true
  }

  onSelectImage(event: any){
    const file = event.files[0]
    if(file.size > MAX_FILE){
      this.messageService.add({
        severity: "error",
        detail: "File bạn chọn vượt quá giới hạn (> 3mb)",
        summary:"Lỗi"
      })
      return;
    }
    if(!MIME_TYPES.includes(file.type)){
      this.messageService.add({
        severity: "error",
        detail: "Loại fileUploadValidator không đúng, vui lòng chọn lại (chỉ chấp nhận: image/png,image/jpeg,image/webp)",
        summary:"Lỗi"
      })
      return;
    }
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
    ).pipe(takeUntil(this.destroy$)).subscribe({
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
        lastName: this.changeInformationForm.get("lastName")?.value,
        summary:this.changeInformationForm.get("summary")?.value
      }
      this.isLoading = true
      this.userService.changeInformation(data).pipe(takeUntil(this.destroy$)).subscribe({
        next:(response) =>{
          this.messageService.add({
            severity: "success",
            detail: response.message,
            summary:"Thành công"
          })
          this.isLoading = false
        },
        error:(error) =>{
          this.messageService.add({
            severity: "error",
            detail: error,
            summary:"Lỗi"
          })
          this.isLoading = false
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

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
