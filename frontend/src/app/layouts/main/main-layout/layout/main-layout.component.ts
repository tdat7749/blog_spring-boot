import { Component } from '@angular/core';
import {AuthService} from "../../../../core/services/auth.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Login} from "../../../../core/types/auth.type";
import {mergeMap, switchMap} from "rxjs";

@Component({
  selector: 'main-layout',
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.css']
})
export class MainLayoutComponent {
  loginForm: FormGroup;
  constructor(private authService:AuthService,private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      userName: '',
      password:'',
    })
  }

  login(){
    const login: Login = this.loginForm.value;
    const response = this.authService.login(login).pipe(
      switchMap(() => this.authService.getMe())
    ).subscribe(
      (response) =>{
      console.log(response);
      this.authService.setCurrentUser(response.data)
    })
  }
}
