import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {passwordsMatch} from "../../../../shared/validators/password-smatch.validator";
import {noWhiteSpaceValidator} from "../../../../shared/validators/no-white-space.validator";
import {ChangePassword} from "../../../../core/types/user.type";
import {UserService} from "../../../../core/services/user.service";
import {MessageService} from "primeng/api";
import {Subject, takeUntil} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {SelectPathService} from "../../../../core/services/select-path.service";

@Component({
  selector: 'main-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ChangePasswordComponent implements OnInit,OnDestroy{

  changePasswordForm:FormGroup
  isLoading:boolean = false

  destroy$ = new Subject<void>()

  constructor(
      private userService:UserService,
      private messageService:MessageService,
      private fb:FormBuilder,
      private _router:ActivatedRoute,
      private selectPathService:SelectPathService
  ) {

  }

  ngOnInit() {
    this._router.url.pipe(takeUntil(this.destroy$)).subscribe(url => {
      this.selectPathService.path$.next(url[0].path)
    })
    this.changePasswordForm = this.fb.group({
      oldPassword:[
        '',
        Validators.compose([
          Validators.required
        ])
      ],
      newPassword:[
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(6),
          Validators.maxLength(50),
          noWhiteSpaceValidator()
        ])
      ],
      confirmPassword:[
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(6),
          Validators.maxLength(50),
          noWhiteSpaceValidator()
        ])
      ],
    },{
      validators: passwordsMatch('newPassword','confirmPassword')
    })
  }

  onChangePassword(){
    const data: ChangePassword = this.changePasswordForm.value

    this.isLoading = true
    this.userService.changePassword(data).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.messageService.add({
          severity:"success",
          detail:response.message,
          summary:"Thành công"
        })
        this.isLoading = false
        this.changePasswordForm.reset()
      },
      error:(error) =>{
        this.messageService.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.isLoading = false
      }
    })
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
