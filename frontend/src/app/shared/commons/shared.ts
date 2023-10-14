import {AbstractControl, ValidatorFn} from "@angular/forms";

export const listNav = [
    {
        title:"TRANG CHỦ",
        path:"/"
    },
    {
        title:"SERIES",
        path:"/series"
    },
    {
        title:"TAGS",
        path:"/tags"
    },
    {
        title:"VỀ CHÚNG TÔI",
        path:"/ve-chung-toi"
    },
    {
        title:"LIÊN HỆ",
        path:"/lien-he"
    },
]


export function NoWhiteSpaceValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
        let controlVal = control.value;
        if (typeof controlVal === 'number') {
            controlVal = `${controlVal}`;
        }
        let isWhitespace = (controlVal || '').trim().length === 0;
        let isValid = !isWhitespace;
        return isValid ? null : { whitespace: 'value is only whitespace' };
    };
}

export function removeSpecialCharacters(){

}