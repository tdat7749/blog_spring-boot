import {Component, OnDestroy, OnInit} from '@angular/core';
import {TagService} from "../../../core/services/tag.service";
import {MessageService} from "primeng/api";
import {LoadingService} from "../../../core/services/loading.service";
import {Tag} from "../../../core/types/tag.type";
import {Subject, takeUntil} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {SelectPathService} from "../../../core/services/select-path.service";

@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.css']
})
export class TagComponent implements OnInit,OnDestroy{

  listTag: Tag[] = []

  destroy$ = new Subject<void>()

  constructor(
      private tagService:TagService,
      private messageService:MessageService,
      public loadingService:LoadingService,
      private _router:ActivatedRoute,
      private selectPathService:SelectPathService
      ) {
  }

  ngOnInit() {
    this._router.parent?.url.pipe(takeUntil(this.destroy$)).subscribe(url => {
      this.selectPathService.path$.next(url[0].path)
    })

    this.loadingService.startLoading()
    this.tagService.getAllTag().pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.listTag = response.data
        this.loadingService.stopLoading()
      },
      error:(error) =>{
        this.messageService.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.listTag = []
        this.loadingService.stopLoading()
      }
    })
  }

  ngOnDestroy() {
    this.selectPathService.path$.next("")
    this.destroy$.next()
    this.destroy$.complete()
  }
}
