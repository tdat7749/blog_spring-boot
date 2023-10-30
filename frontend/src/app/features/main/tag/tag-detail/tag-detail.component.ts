import {Component, OnDestroy, OnInit} from '@angular/core';
import {combineLatest, debounceTime, distinctUntilChanged, Observable, Subject, switchMap, takeUntil, tap} from "rxjs";
import {SortBy} from "../../../../core/types/api-response.type";
import {MessageService} from "primeng/api";
import {LoadingService} from "../../../../core/services/loading.service";
import {PaginationService} from "../../../../core/services/pagination.service";
import {PostService} from "../../../../core/services/post.service";
import {PostList} from "../../../../core/types/post.type";
import {ActivatedRoute, Router} from "@angular/router";
import {Tag} from "../../../../core/types/tag.type";
import {TagService} from "../../../../core/services/tag.service";

@Component({
  selector: 'app-tag-detail',
  templateUrl: './tag-detail.component.html',
  styleUrls: ['./tag-detail.component.css']
})
export class TagDetailComponent implements OnInit,OnDestroy{
  search$:Observable<[number,string,SortBy]>
  destroy$ = new Subject<void>()

  totalPage:number
  tag:Tag
  listPost:PostList[] = []
  slug:string

  isGetLoading:boolean = false

  constructor(
      private postService:PostService,
      private messageService:MessageService,
      public loadingService:LoadingService,
      private paginationService:PaginationService,
      private _router:ActivatedRoute,
      private tagService:TagService,
      private router:Router
  ){

  }

  ngOnInit(){
    this._router.paramMap.pipe(takeUntil(this.destroy$)).subscribe(params => {
      this.slug = params.get("slug") as string
    })

    this.loadingService.startLoading()
    this.tagService.getTagBySlug(this.slug).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.tag = response.data
        this.loadingService.stopLoading()
      },
      error:(error)=>{
        this.messageService.add({
          severity: "error",
          detail: error,
          summary: "Lỗi"
        })
        this.loadingService.stopLoading()
        this.router.navigate(["/"])
        return;
      }
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
          return this.postService.getAllPostByTag(keyword,this.slug,pageIndex,sortBy)
        })
    ).subscribe({
      next:(response) =>{
        this.totalPage = response.data.totalPage
        this.listPost = response.data.data
        this.isGetLoading = false
      },
      error:(error) => {
        this.messageService.add({
          severity: "error",
          detail: error,
          summary: "Lỗi"
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
