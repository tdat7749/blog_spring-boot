import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../../core/services/auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Register} from "../../../core/types/auth.type";
import {MessageService} from "primeng/api";
import {noWhiteSpaceValidator} from "../../../shared/validators/no-white-space.validator";
import {passwordsMatch} from "../../../shared/validators/password-smatch.validator";
import {Subject, takeUntil} from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit,OnDestroy{

  registerForm:FormGroup

  isLoading:boolean = false

  destroy$ = new Subject<void>()

  constructor(private authService:AuthService,private messageService:MessageService,private fb:FormBuilder) {
  }

  ngOnInit() {
    this.registerForm = this.fb.group({
      userName:[
          '',
          Validators.compose([
              Validators.required,
              Validators.minLength(6),
              Validators.maxLength(50),
              noWhiteSpaceValidator()
          ])
      ],
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
      email:[
        '',
        Validators.compose([
          Validators.required,
          Validators.email,
          noWhiteSpaceValidator()
        ])
      ],
      password:[
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
      ]
    },{
      validators: passwordsMatch('password','confirmPassword')
    })
  }

  onRegister(){
    this.isLoading = true
    const data:Register = this.registerForm.value
    this.authService.register(data).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.messageService.add({
          severity: "success",
          detail: response.message,
          summary:"Thành công"
        })
        this.isLoading = false
      },
      error:(error =>{
        this.messageService.add({
          severity: "error",
          detail: error,
          summary:"Lỗi"
        })
        this.isLoading = false
      }),
      complete:() => {

      }
    })
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
