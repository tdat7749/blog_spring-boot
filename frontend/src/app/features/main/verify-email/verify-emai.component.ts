import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../../core/services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {VerifyEmail} from "../../../core/types/auth.type";
import {map} from "rxjs";

@Component({
    selector:"main-verify-account",
    templateUrl:"./verify-email.component.html"
})

export class VerifyEmailComponent implements OnInit{

    email:string
    code:string
    isLoading:boolean = false
    successMessage:string | null = null
    errorMessage:string | null = null
    constructor(private authService:AuthService,private router:Router,private _router:ActivatedRoute) {

    }

    ngOnInit() {
        this.errorMessage = null
        this.successMessage = null
        this._router.queryParams.subscribe(params =>{
            this.email = params?.['email']
            this.code = params?.['code']
        }).unsubscribe()

        if(this.email === undefined || this.code === undefined){
            this.router.navigate(['/'])
        }

        const dataVerify:VerifyEmail = {
            email: this.email,
            code: this.code
        }
        this.isLoading = true
        this.authService.verifyEmail(dataVerify).subscribe({
            next:(response) =>{
                this.isLoading = false
                this.successMessage = response.message
            },
            error:(response => {
                this.isLoading = false
                this.errorMessage = response
            })
        })
    }
}