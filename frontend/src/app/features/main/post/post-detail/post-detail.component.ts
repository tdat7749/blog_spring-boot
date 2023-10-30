import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { PostService } from "../../../../core/services/post.service";
import { Post, TOC } from "../../../../core/types/post.type";
import { ActivatedRoute, Router } from "@angular/router";
import { MessageService } from "primeng/api";
import { forkJoin, Subject, takeUntil } from "rxjs";
import { LoadingService } from "../../../../core/services/loading.service";
import { AuthService } from "../../../../core/services/auth.service";
import { generateTOC } from 'src/app/shared/commons/generate-toc';
import {User} from "../../../../core/types/user.type";

@Component({
  selector: 'main-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PostDetailComponent implements OnInit, OnDestroy, AfterViewInit {

  isLiked: boolean = false
  slug: string
  post: Post
  isLoading: boolean = false

  content: string

  toc: TOC[]

  destroy$ = new Subject<void>()

  timeOut: any

  userLikePostVisible:boolean = false
  listUserLikePost:User[] = []

  constructor(
    private postService: PostService,
    private router: Router,
    private _router: ActivatedRoute,
    private messageService: MessageService,
    public loadingService: LoadingService,
    private authService: AuthService,
    private elementRef:ElementRef
  ) {

  }



  ngOnInit() {
    this._router.paramMap.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      this.slug = params.get("slug") as string
    })

    if (!this.slug) {
      this.router.navigate(["/"])
    }

    this.loadingService.startLoading()
    forkJoin([
      this.postService.getPostDetailBySlug(this.slug),
      this.postService.checkUserLikePost(this.slug),
      this.postService.getAllUserLikePost(this.slug)
    ], (postResponse, checkLikedResponse,listUserLikePost) => {
      return {
        postResponse: postResponse,
        checkLikedResponse: checkLikedResponse,
        userLikePostResponse:listUserLikePost
      }
    }).pipe(takeUntil(this.destroy$)).subscribe({
      next: (response) => {
        this.post = response.postResponse.data
        this.isLiked = response.checkLikedResponse.data
        this.toc = generateTOC(response.postResponse.data.content)
        this.listUserLikePost = response.userLikePostResponse.data
        this.loadingService.stopLoading()
      },
      error: (error) => {
        this.messageService.add({
          severity: "error",
          detail: error,
          summary: "Lỗi"
        })
        this.router.navigate(["/"])
        this.loadingService.stopLoading()
        return;
      }
    })
  }

  addIdToHeading(content: string) {
    const parser = new DOMParser();
    const doc = parser.parseFromString(content, 'text/html');
    const headings = doc.body.querySelectorAll('h1, h2, h3');

    headings.forEach((heading) => {
      const title = heading.textContent as string;
      const id = title.toLowerCase().replace(/\s+/g, '-') || "";
      heading.setAttribute('id', id);
    });

    const serializer = new XMLSerializer();
    return serializer.serializeToString(doc);
  }

  ngAfterViewInit() {
    this.timeOut = setTimeout(() => {
      this.postService.plusView(this.slug)
        .pipe(takeUntil(this.destroy$)).subscribe()
    }, 10000)
  }

  onLikePost(postId: number) {
    if (this.authService.getCurrentUser() === null) {
      this.messageService.add({
        severity: "error",
        detail: "Vui lòng đăng nhập",
        summary: "Lỗi"
      })
      return;
    }
    this.isLoading = true
    this.postService.likePost(postId).pipe(takeUntil(this.destroy$)).subscribe({
      next: (response) => {
        this.messageService.add({
          severity: "success",
          detail: response.message,
          summary: "Thành Công"
        })
        this.isLoading = false
        this.isLiked = true
      },
      error: (error) => {
        this.messageService.add({
          severity: "error",
          detail: error,
          summary: "Lỗi"
        })
        this.isLoading = false
      }
    })
  }

  onUnLikePost(postId: number) {
    if (this.authService.getCurrentUser() === null) {
      this.messageService.add({
        severity: "error",
        detail: "Vui lòng đăng nhập",
        summary: "Lỗi"
      })
      return;
    }
    this.isLoading = true
    this.postService.unlikePost(postId).pipe(takeUntil(this.destroy$)).subscribe({
      next: (response) => {
        this.messageService.add({
          severity: "success",
          detail: response.message,
          summary: "Thành Công"
        })
        this.isLoading = false
        this.isLiked = false
      },
      error: (error) => {
        this.messageService.add({
          severity: "error",
          detail: error,
          summary: "Lỗi"
        })
        this.isLoading = false
      }
    })
  }

  ngOnDestroy() {
    clearTimeout(this.timeOut)
    this.destroy$.next()
    this.destroy$.complete()
  }
}
