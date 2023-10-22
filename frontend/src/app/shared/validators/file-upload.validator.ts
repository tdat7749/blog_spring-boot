import {MAX_FILE, MIME_TYPES} from "../commons/shared";

export const fileUploadValidator = (file:File):{success:boolean,message:string} => {
    if(file.size > MAX_FILE){
        return {success:false,message:"File bạn chọn vượt quá giới hạn (> 3mb)"};
    }
    if(!MIME_TYPES.includes(file.type)){
        return {success:false,message:"Loại fileUploadValidator không đúng, vui lòng chọn lại (chỉ chấp nhận: image/png,image/jpeg,image/webp"};
    }

    return {success:true,message:"OK"}
}