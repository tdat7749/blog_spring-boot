import { Component } from '@angular/core';
import {AuthService} from "../../../../core/services/auth.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Login} from "../../../../core/types/auth.type";
import {concatMap} from "rxjs";
import {CookieService} from "ngx-cookie-service";
import {MessageService} from "primeng/api";
import {ApiResponse} from "../../../../core/types/api-response.type";
import {Token} from "../../../../core/types/token.type";
import {User} from "../../../../core/types/user.type";

@Component({
  selector: 'main-layout',
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.css']
})
export class MainLayoutComponent {
  loginForm: FormGroup;
  public isLoading:boolean = false;
  public isGetLoading:boolean = false;
  constructor(private authService:AuthService,private cookieService: CookieService,private messageService:MessageService,private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      userName: '',
      password:'',
    })
  }

  login(){
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
        this.isLoading = false
        this.messageService.add({
          severity: "success",
          detail: response.message,
          summary:"Thông báo"
        })
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
