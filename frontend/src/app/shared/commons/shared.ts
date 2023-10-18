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
        title:"Tạo Bài Viết",
        path:"tao-bai-viet"
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