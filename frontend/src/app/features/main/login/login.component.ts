import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../../core/services/auth.service";
import {NoWhiteSpaceValidator} from "../../../shared/commons/shared";
import {Login} from "../../../core/types/auth.type";
import {concatMap} from "rxjs";
import {ApiResponse} from "../../../core/types/api-response.type";
import {Token} from "../../../core/types/token.type";
import {User} from "../../../core/types/user.type";
import {CookieService} from "ngx-cookie-service";
import {MessageService} from "primeng/api";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginForm: FormGroup
  isLoading: boolean
  constructor(private authService:AuthService,private cookieService:CookieService,private messageService:MessageService,private fb:FormBuilder,private router:Router) {

  }

  ngOnInit() {
    this.loginForm = this.fb.group({
      userName:[
          '',
          Validators.compose([
              Validators.required,
              NoWhiteSpaceValidator()
          ])
      ],
      password:[
          '',
          Validators.compose([
              Validators.required,
              NoWhiteSpaceValidator()
          ])
      ]
    })
      console.log(this.loginForm.errors)
  }
    onLogin(){
        this.isLoading = true
        const login: Login = this.loginForm.value;
        const response = this.authService.login(login).pipe(
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
