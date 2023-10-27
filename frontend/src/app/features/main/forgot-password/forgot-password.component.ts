import {Component, OnDestroy, OnInit} from "@angular/core";
import {UserService} from "../../../core/services/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ForgotPassword} from "../../../core/types/user.type";
import {MessageService} from "primeng/api";
import {ActivatedRoute, Router} from "@angular/router";
import {passwordsMatch} from "../../../shared/validators/password-smatch.validator";
import {noWhiteSpaceValidator} from "../../../shared/validators/no-white-space.validator";
import {Subject, takeUntil} from "rxjs";

@Component({
    selector:"main-forgot-password",
    templateUrl:"./forgot-password.component.html"
})

export class ForgotPasswordComponent implements OnInit,OnDestroy{

    forgotPasswordForm: FormGroup
    isLoading: boolean = false

    email:string | undefined
    code:string | undefined

    destroy$ = new Subject<void>()

    constructor(
        private userService:UserService,
        private messageService:MessageService,
        private fb:FormBuilder,
        private router:Router,
        private _router:ActivatedRoute
    ) {

    }

    ngOnInit() {
        this._router.queryParams.pipe(takeUntil(this.destroy$)).subscribe(params =>{
            this.email = params?.["email"]
            this.code = params?.["code"]
        })

        if(this.code === undefined || this.email === undefined){
            this.router.navigate(["/"])
        }

        // create form
        this.forgotPasswordForm = this.fb.group({
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

    onForgotPassword(){
        this.isLoading = true
        const formData = this.forgotPasswordForm.value
        const data:ForgotPassword = {
            ...formData,
            code: this.code,
            email: this.email
        }
        this.userService.forgotPassword(data).pipe(takeUntil(this.destroy$)).subscribe({
            next:(response) =>{
                this.messageService.add({
                    severity: "success",
                    detail: response.message,
                    summary:"Thành công"
                })
                this.isLoading = false
                this.router.navigate(['/dang-nhap'])
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

    ngOnDestroy() {
        this.destroy$.next()
        this.destroy$.complete()
    }
}