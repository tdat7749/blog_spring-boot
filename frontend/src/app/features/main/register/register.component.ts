import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../core/services/auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NoWhiteSpaceValidator} from "../../../shared/commons/shared";
import {Register} from "../../../core/types/auth.type";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{

  registerForm:FormGroup

  isLoading:boolean = false
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
              NoWhiteSpaceValidator()
          ])
      ],
      firstName:[
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(60),
          NoWhiteSpaceValidator()
        ])
      ],
      lastName:[
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(70),
          NoWhiteSpaceValidator()
        ])
      ],
      email:[
        '',
        Validators.compose([
          Validators.required,
          Validators.email,
          NoWhiteSpaceValidator()
        ])
      ],
      password:[
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(6),
          Validators.maxLength(50),
          NoWhiteSpaceValidator()
        ])
      ],
      confirmPassword:[
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(6),
          Validators.maxLength(50),
          NoWhiteSpaceValidator()
        ])
      ]
    })
  }

  onRegister(){
    this.isLoading = true
    const data:Register = this.registerForm.value
    this.authService.register(data).subscribe({
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
}
