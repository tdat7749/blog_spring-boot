import {AbstractControl, ValidatorFn} from "@angular/forms";

export function noWhiteSpaceValidator(): ValidatorFn {
    return (control:AbstractControl) : {[key:string] : any} | null => {
        let valueControl = control.value
        if(!valueControl){
            return null
        }
        if (typeof valueControl === 'number') {
            valueControl = `${valueControl}`;
        }
        if(valueControl.length === 0){
            return null
        }

        const isWhiteSpace = valueControl.trim().length === 0
        const isValid = !isWhiteSpace

        return isValid ? null : { whitespace: 'value is only whitespace' }
    }
}