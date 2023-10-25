import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild, ViewChildren, ViewEncapsulation} from '@angular/core';
import {PostService} from "../../../../core/services/post.service";
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
import {SortBy} from "../../../../core/types/api-response.type";
import {MessageService} from "primeng/api";
import {PostList} from "../../../../core/types/post.type";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {noWhiteSpaceValidator} from "../../../../shared/validators/no-white-space.validator";
import {Editor} from "primeng/editor";
import {FileUpload} from "primeng/fileupload";
import {MAX_FILE, MIME_TYPES} from "../../../../shared/commons/shared";
import {PaginationService} from "../../../../core/services/pagination.service";
import {LoadingService} from "../../../../core/services/loading.service";

@Component({
  selector: 'main-post-management',
  templateUrl: './post-management.component.html',
  styleUrls: ['./post-management.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PostManagementComponent implements OnInit,OnDestroy{

  isLoading:boolean = false

  listPost:PostList[] = []
  totalPage:number
  totalRecord:number


  search$:Observable<[number,string,SortBy]>


  destroy$ = new Subject<void>()


  constructor(
      private paginationService:PaginationService,
      private postService:PostService,
      private messageService:MessageService,
      public loadingService:LoadingService
  ) {

  }

  ngOnInit() {
    this.search$ = combineLatest([
        this.paginationService.pageIndex$,
        this.paginationService.keyword$,
        this.paginationService.sortBy$
    ])
    this.search$.pipe(
        takeUntil(this.destroy$),
        tap(() => this.loadingService.startLoading()),
        debounceTime(700),
        distinctUntilChanged(), // chỉ gọi lại khi có giá trị thay đổi
        switchMap(([pageIndex,keyword,sortBy]) =>{
          return this.postService.getAllPostByCurrentUser(keyword,pageIndex,sortBy)
        })
    ).subscribe({
      next:(response) =>{
        this.listPost = response.data.data
        this.totalPage = response.data.totalPage
        this.totalRecord = response.data.totalRecord
        this.loadingService.stopLoading()
      },
      error:(error) =>{
        this.messageService.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.loadingService.stopLoading()
      }
    })
  }


  onChangeSearch(event:any){
    this.paginationService.updateKeyword(event.target.value)
  }

  onChangePageIndex(event: any){
      this.paginationService.updatePageIndex(event.page)
  }

  onChangeSort(sortBy:SortBy){
    this.paginationService.updateSortBy(sortBy)
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
