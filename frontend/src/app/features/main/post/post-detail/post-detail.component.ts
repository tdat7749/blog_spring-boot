import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {PostService} from "../../../../core/services/post.service";
import {Post} from "../../../../core/types/post.type";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {forkJoin} from "rxjs";

@Component({
  selector: 'main-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PostDetailComponent implements OnInit{

  isLiked:boolean = false
  slug:string
  post:Post
  isLoading: boolean = false
  constructor(
      private postService:PostService,
      private router:Router,
      private _router:ActivatedRoute,
      private messageService:MessageService
  ) {

  }

  ngOnInit() {
    this._router.paramMap.subscribe((params) =>{
      this.slug = params.get("slug") as string
    })

    if(!this.slug){
      this.router.navigate(["/"])
    }
    this.isLoading = true
    forkJoin([
      this.postService.getPostDetailBySlug(this.slug),
      this.postService.checkUserLikePost(this.slug)
    ],(postResponse,checkLikedResponse) => {
      return {
        postResponse:postResponse,
        checkLikedResponse:checkLikedResponse
      }
    }).subscribe({
      next:(response) =>{
        this.post = response.postResponse.data
        this.isLiked = response.checkLikedResponse.data
        console.log(response.checkLikedResponse)
        this.isLoading = false
      },
      error:(error) =>{
        this.messageService.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.router.navigate(["/"])
        this.isLoading = false
        return;
      }
    })
  }

  onLikePost(postId:number){
    this.isLoading = true
    this.postService.likePost(postId).subscribe({
      next:(response) =>{
        this.messageService.add({
          severity:"success",
          detail:response.message,
          summary:"Thành Công"
        })
        this.isLoading = false
        this.isLiked = true
      },
      error:(error) =>{
        this.messageService.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.isLoading = false
      }
    })
  }

  onUnLikePost(postId:number){
    this.isLoading = true
    this.postService.unlikePost(postId).subscribe({
      next:(response) =>{
        this.messageService.add({
          severity:"success",
          detail:response.message,
          summary:"Thành Công"
        })
        this.isLoading = false
        this.isLiked = false
      },
      error:(error) =>{
        this.messageService.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.isLoading = false
      }
    })
  }
}
