import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {forkJoin, retry, Subject, takeUntil} from "rxjs";
import {PostList} from "../../../core/types/post.type";
import {MessageService} from "primeng/api";
import {PostService} from "../../../core/services/post.service";
import {TagService} from "../../../core/services/tag.service";
import {LoadingService} from "../../../core/services/loading.service";
import {Tag} from "../../../core/types/tag.type";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class HomeComponent implements OnInit,OnDestroy{

  postLatest:PostList[] = []

  postMostView:PostList[] = []

  listTag: Tag[] = []

  destroy$ = new Subject<void>()

  constructor(
      private messageService:MessageService,
      private postService:PostService,
      private tagService:TagService,
      public loadingService:LoadingService
  ) {

  }

  ngOnInit() {
    this.loadingService.startLoading()
    forkJoin([
        this.postService.getListPostLatest(),
        this.postService.getListPostMostView(),
        this.tagService.getAllTag()
    ],(postLatest,postMostView,listTag) =>{
      return {
        postLatest,
        postMostView,
        listTag
      }
    }).pipe(takeUntil(this.destroy$),retry(3)).subscribe({
      next:(response) => {
        this.postLatest = response.postLatest.data
        this.postMostView = response.postMostView.data
        this.listTag = response.listTag.data
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

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
