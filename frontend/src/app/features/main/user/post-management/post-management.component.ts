import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild, ViewChildren, ViewEncapsulation } from '@angular/core';
import { PostService } from "../../../../core/services/post.service";
import {
  BehaviorSubject,
  combineLatest,
  debounceTime,
  distinctUntilChanged,
  Observable,
  Subject,
  switchMap, takeUntil,
  tap
} from "rxjs";
import { SortBy } from "../../../../core/types/api-response.type";
import {ConfirmationService, ConfirmEventType, MenuItem, MessageService} from "primeng/api";
import { PostList } from "../../../../core/types/post.type";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { noWhiteSpaceValidator } from "../../../../shared/validators/no-white-space.validator";
import { Editor } from "primeng/editor";
import { FileUpload } from "primeng/fileupload";
import { MAX_FILE, MIME_TYPES } from "../../../../shared/commons/shared";
import { PaginationService } from "../../../../core/services/pagination.service";
import { LoadingService } from "../../../../core/services/loading.service";
import {ActivatedRoute} from "@angular/router";
import {SelectPathService} from "../../../../core/services/select-path.service";

@Component({
  selector: 'main-post-management',
  templateUrl: './post-management.component.html',
  styleUrls: ['./post-management.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PostManagementComponent implements OnInit, OnDestroy {

  isLoading: boolean = false

  listPost: PostList[] = []
  totalPage: number
  totalRecord: number


  search$: Observable<[number, string, SortBy]>


  destroy$ = new Subject<void>()



  constructor(
    private paginationService: PaginationService,
    private postService: PostService,
    private messageService: MessageService,
    public loadingService: LoadingService,
    private confirmService:ConfirmationService,
    private _router:ActivatedRoute,
    private selectPathService:SelectPathService
  ) {

  }

  ngOnInit() {
    this._router.url.pipe(takeUntil(this.destroy$)).subscribe(url => {
      this.selectPathService.path$.next(url[0].path)
    })
    this.search$ = combineLatest([
      this.paginationService.pageIndex$,
      this.paginationService.keyword$,
      this.paginationService.sortBy$
    ]).pipe(takeUntil(this.destroy$))
    this.search$.pipe(
      takeUntil(this.destroy$),
      tap(() => this.loadingService.startLoading()),
      debounceTime(700),
      distinctUntilChanged(), // chỉ gọi lại khi có giá trị thay đổi
      switchMap(([pageIndex, keyword, sortBy]) => {
        return this.postService.getAllPostByCurrentUser(keyword, pageIndex, sortBy)
      })
    ).subscribe({
      next: (response) => {
        this.listPost = response.data.data
        this.totalPage = response.data.totalPage
        this.totalRecord = response.data.totalRecord
        this.loadingService.stopLoading()
      },
      error: (error) => {
        this.messageService.add({
          severity: "error",
          detail: error,
          summary: "Lỗi"
        })
        this.loadingService.stopLoading()
      }
    })
  }


  onChangeSearch(event: any) {
    this.paginationService.updateKeyword(event.target.value)
  }

  onChangePageIndex(event: any) {
    this.paginationService.updatePageIndex(event.page)
  }

  onDeletePost(id:number,event:Event){
    this.confirmService.confirm({
      target:event.target as EventTarget,
      message:"Bạn có chắc chắn muốn xóa bài viết này ?",
      header:"Xóa Bài Viết",
      icon:"pi pi-exclamation-triangle",
      accept:() => {
        this.isLoading = true
        this.postService.deletePost(id).pipe(takeUntil(this.destroy$))
            .subscribe({
              next:(response) =>{
                this.listPost = this.listPost.filter((item) => item.id !== id)
                this.messageService.add({
                  severity:"success",
                  detail: response.message,
                  summary:"Thành Công"
                })
                this.isLoading = false
              },
              error:(error) => {
                this.messageService.add({
                  severity:"error",
                  detail: error,
                  summary:"Thất Bại"
                })
                this.isLoading = false
              }
            })
      },
      reject:() => {
        this.messageService.add({ severity: 'info', summary: 'Thông Báo', detail: 'Bạn đã hủy việc xóa bài viết' });
      }
    })
  }

  ngOnDestroy() {
    this.paginationService.onDestroy()
    this.destroy$.next()
    this.destroy$.complete()
  }
}
