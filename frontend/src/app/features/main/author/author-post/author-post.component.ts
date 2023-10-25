import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {combineLatest, debounceTime, distinctUntilChanged, Observable, Subject, switchMap, takeUntil, tap} from "rxjs";
import {SortBy} from "../../../../core/types/api-response.type";
import {PostList} from "../../../../core/types/post.type";
import {ActivatedRoute, Router} from "@angular/router";
import {PostService} from "../../../../core/services/post.service";
import {MessageService} from "primeng/api";
import {PaginationService} from "../../../../core/services/pagination.service";

@Component({
  selector: 'main-author-post',
  templateUrl: './author-post.component.html',
  styleUrls: ['./author-post.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AuthorPostComponent implements OnInit,OnDestroy{
  isLoading:boolean = false

  userName:string

  totalPage:number
  search$:Observable<[number,string,SortBy]>

  listPost:PostList[] = []

  isGetLoading:boolean = false

  destroy$ = new Subject<void>()

  constructor(
      private _router:ActivatedRoute,
      private postService:PostService,
      private messageService:MessageService,
      private paginationService:PaginationService,
  ) {

  }

  ngOnInit() {
    this._router.paramMap.pipe(takeUntil(this.destroy$)).subscribe(params => {
      this.userName = params.get("userName") as string
    })

    this.search$ = combineLatest([
      this.paginationService.pageIndex$,
      this.paginationService.keyword$,
      this.paginationService.sortBy$
    ])

    this.search$.pipe(
        takeUntil(this.destroy$),
        tap(() => this.isGetLoading = true),
        debounceTime(700),
        distinctUntilChanged(),
        switchMap(([pageIndex,keyword,sortBy]) => {
          return this.postService.getAllPostOfAuthorByUserName(keyword,this.userName,pageIndex,sortBy)
        })
    ).subscribe({
      next:(response) => {
        this.listPost = response.data.data
        this.totalPage = response.data.totalPage
        this.isGetLoading = false
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

  onChangeSearch(event:any){
    this.paginationService.updateKeyword(event.target.value)
  }

  onChangePageIndex(event: any){
    this.paginationService.updatePageIndex(event.page)
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
