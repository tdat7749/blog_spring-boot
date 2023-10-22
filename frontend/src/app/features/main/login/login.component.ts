import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../../core/services/auth.service";
import {Login} from "../../../core/types/auth.type";
import {concatMap} from "rxjs";
import {ApiResponse} from "../../../core/types/api-response.type";
import {Token} from "../../../core/types/token.type";
import {User} from "../../../core/types/user.type";
import {CookieService} from "ngx-cookie-service";
import {MessageService} from "primeng/api";
import {Router} from "@angular/router";
import {UserService} from "../../../core/services/user.service";
import {noWhiteSpaceValidator} from "../../../shared/validators/no-white-space.validator";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginForm: FormGroup
  sendEmailForm: FormGroup
  isLoading: boolean
  verifyFormVisible: boolean = false
  forgotPasswordVisible: boolean = false

  constructor(
      private authService:AuthService,
      private cookieService:CookieService,
      private messageService:MessageService,
      private userService:UserService,
      private fb:FormBuilder,
      private router:Router
  ) {

  }

  ngOnInit() {
    this.loginForm = this.fb.group({
      userName:[
          '',
          Validators.compose([
              Validators.required,
              noWhiteSpaceValidator()
          ])
      ],
      password:[
          '',
          Validators.compose([
              Validators.required,
              noWhiteSpaceValidator()
          ])
      ]
    })

      this.sendEmailForm = this.fb.group({
          email:[
              '',
              Validators.compose([
                  Validators.email,
                  Validators.required
              ])
          ]
      })
  }

  showSendEmailVerifyDialog(){
      this.verifyFormVisible = true
  }

  showSendEmailForgotPassword(){
      this.forgotPasswordVisible = true
  }



  onSendEmailVerify(){
      this.isLoading = true
      const email:string = this.sendEmailForm.value.email

      this.authService.resendVerifyEmail(email).subscribe({
          next:(response) =>{
              this.messageService.add({
                  severity: "success",
                  detail: response.message,
                  summary:"Thành công"
              })
              this.isLoading = false
              this.verifyFormVisible = false
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
  }

  onSendEmailForgotPassword(){
      this.isLoading = true
      const email:string = this.sendEmailForm.value.email

      this.userService.getEmailForgotPassword(email).subscribe({
          next:(response) =>{
              this.messageService.add({
                  severity: "success",
                  detail: response.message,
                  summary:"Thành công"
              })
              this.isLoading = false
              this.forgotPasswordVisible = false
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
  }

    onLogin(){
        this.isLoading = true
        const login: Login = this.loginForm.value;
        this.authService.login(login).pipe(
            concatMap((response: ApiResponse<Token>) =>{
                this.cookieService.set("refreshToken",response.data.refeshToken);
                this.cookieService.set("accessToken",response.data.accessToken);

                return this.authService.getMe()
            })
        ).subscribe({
            next: (response:ApiResponse<User>) =>{
                this.authService.userState$.next(response.data)
                this.authService.setCurrentUser(response.data)
                this.messageService.add({
                    severity: "success",
                    detail: response.message,
                    summary:"Thành công"
                })
                this.isLoading = false
                this.router.navigate(["/"])
            },
            error: (error) =>{
                this.messageService.add({
                    severity: "error",
                    detail: error,
                    summary:"Lỗi"
                })
                this.isLoading = false
            }
        })
    }
}
