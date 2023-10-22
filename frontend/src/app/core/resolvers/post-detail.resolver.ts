// import {ResolveFn} from "@angular/router";
// import {inject} from "@angular/core";
// import {PostService} from "../services/post.service";
// import {ApiResponse} from "../types/api-response.type";
// import {Post} from "../types/post.type";
// import {MessageService} from "primeng/api";
// import {Observable} from "rxjs";
//
// export const PostDetailResolver:ResolveFn<ApiResponse<Post> | boolean> = (route, state):Observable<ApiResponse<Post> | boolean> =>{
//     const postService = inject(PostService)
//     const messageService = inject(MessageService)
//     const slug = route.paramMap.get("slug") as string
//     postService.getPostDetailBySlug(slug).subscribe({
//         next:(response:ApiResponse<Post>) =>{
//             return response
//         },
//         error:(error:string) =>{
//             messageService.add({
//                 detail:error,
//                 summary:"Lỗi",
//                 severity: "error"
//             })
//             return false
//         }
//     })
// }