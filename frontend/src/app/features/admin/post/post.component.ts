import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {PostService} from "../../../core/services/post.service";
import {ConfirmationService, MenuItem, MessageService} from "primeng/api";
import {LoadingService} from "../../../core/services/loading.service";
import {PaginationService} from "../../../core/services/pagination.service";
import {combineLatest, debounceTime, distinctUntilChanged, Observable, Subject, switchMap, takeUntil, tap} from "rxjs";
import {Post, PostList, UpdatePostStatus} from "../../../core/types/post.type";
import {SortBy} from "../../../core/types/api-response.type";

@Component({
  selector: 'app-admin-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PostComponent implements OnInit,OnDestroy{

  isLoading: boolean = false
  isGetLoading: boolean = false

  listPost: PostList[] = []
  post:Post | null = null
  totalPage: number

  detailDialogVisible:boolean = false

  search$: Observable<[number, string, SortBy]>

  destroy$ = new Subject<void>()
  destroyPaging$ = new Subject<void>()

  items: MenuItem[] | undefined;

  activeItem: MenuItem | undefined;


  constructor(
      private postService:PostService,
      private messageService:MessageService,
      private confirmService:ConfirmationService,
      public loadingService:LoadingService,
      private paginationService:PaginationService
  ) {
  }

  ngOnInit() {
    this.items = [
      { label: 'Tất Cả', icon: 'pi pi-fw pi-book' }
    ];
    this.activeItem = this.items[0];
    this.getAllPost()
  }

  getAllPost(){
    this.search$ = combineLatest([
      this.paginationService.pageIndex$,
      this.paginationService.keyword$,
      this.paginationService.sortBy$
    ]).pipe(takeUntil(this.destroy$))

    this.search$.pipe(
        takeUntil(this.destroy$),
        tap(() => this.loadingService.startLoading()),
        debounceTime(700),
        distinctUntilChanged(),
        switchMap(([pageIndex, keyword, sortBy]) => {
          console.log(pageIndex,keyword,"all")
          return this.postService.getAllPost(keyword, pageIndex, sortBy)
        })
    ).subscribe({
      next: (response) => {
        this.totalPage = response.data.totalPage
        this.listPost = response.data.data
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

  getAllPostNotPublished(){
    this.search$ = combineLatest([
      this.paginationService.pageIndex$,
      this.paginationService.keyword$,
      this.paginationService.sortBy$
    ]).pipe(takeUntil(this.destroy$))

    this.search$.pipe(
        takeUntil(this.destroy$),
        tap(() => this.loadingService.startLoading()),
        debounceTime(700),
        distinctUntilChanged(),
        switchMap(([pageIndex, keyword, sortBy]) => {
          return this.postService.getAllPostNotPublished(keyword, pageIndex, sortBy)
        })
    ).subscribe({
      next: (response) => {
        this.totalPage = response.data.totalPage
        this.listPost = response.data.data
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

  onChangeStatus(post:PostList){
    this.isLoading = true
    const data:UpdatePostStatus = {
      status: !post.published
    }
    this.postService.updateStatus(post.id,data).pipe(takeUntil(this.destroy$))
        .subscribe({
          next:(response) => {
            this.messageService.add({
              severity:"success",
              detail:response.message,
              summary:"Thành Công"
            })
            this.isLoading = false
            post.published = !post.published
          },
          error:(error) => {
            this.messageService.add({
              severity:"error",
              detail:error,
              summary:"Lỗi"
            })
            this.isLoading = false
          }
        })
  }

  onPreviewDetail(id:number){
    this.isGetLoading = true
    this.postService.getById(id).pipe(takeUntil(this.destroy$))
        .subscribe({
          next:(response) => {
            this.post = response.data
            this.isGetLoading = false
            this.detailDialogVisible = true
          },
          error:(error) => {
            this.messageService.add({
              severity:"error",
              detail:error,
              summary:"Lỗi"
            })
            this.isGetLoading = false
          }
        })
  }

  onActiveItemChange(event: MenuItem) {
    this.activeItem = event;
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
    this.paginationService.onDestroy()
  }
}
