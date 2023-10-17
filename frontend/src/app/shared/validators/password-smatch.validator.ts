import {AbstractControl} from "@angular/forms";

export function passwordsMatch(password:string,confirmPassword:string){
    return (control:AbstractControl) =>{
        const controlPassword = control.get(password)
        const controlConfirmPassword = control.get(confirmPassword)
        if(controlConfirmPassword?.errors && !controlConfirmPassword.errors?.['passwordsMatch']){
            return
        }

        if(controlPassword?.value !== controlConfirmPassword?.value){
            controlConfirmPassword?.setErrors({passwordsMatch: true})
        }else{
            controlConfirmPassword?.setErrors(null)
        }
    }
}