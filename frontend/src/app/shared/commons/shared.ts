import {CreateTag} from "../../core/types/tag.type";
import {hasSpecialCharacters} from "../validators/has-special-characters.validator";
import slugify from "slugify";

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

export const userSideBar = [
    {
        title:"Thông tin tài khoản",
        path:"thong-tin"
    },
    {
        title:"Đổi mật khẩu",
        path:"doi-mat-khau"
    },
    {
        title:"Quản lý bài viết",
        path:"quan-ly-bai-viet"
    },
    {
        title:"Quản lý series",
        path:"quan-ly-series"
    }
]

export function removeSpecialCharacters(){

}

export const MAX_FILE:number = 3145728

export const MIME_TYPES = ["image/png","image/jpeg","image/webp","image/gif"]

export function capitalizeFirstLetter(str:string) {
    return str.replace(/\b\w/g, function(txt:string) {
        return txt.toUpperCase();
    });
}

export const postStatus = [
    {
        title:"Ngừng Hoạt Động",
        status:false
    },
    {
        title:"Đang Hoạt Động",
        status:true
    }
]

export function getNewTagByString(newTagString:string):{data:any,success:boolean}{
    if(newTagString === null || newTagString === undefined || newTagString === "") return {data:null,success:true}
    let flag:boolean = true
    const arrayNewTag:CreateTag[] = newTagString.split(",")
        .map((tag:string) => {
            const value =  tag.replace("_", ' ').trim()
            if(hasSpecialCharacters(value)){
                flag = false
            }
            const data:CreateTag = {
                title: capitalizeFirstLetter(value),
                thumbnail:"a",
                slug: slugify(value.toLowerCase())
            }
            return data
        })

    if(!flag){
        return {data:null,success:false}
    }
    return {data:arrayNewTag,success:true}
}