import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {SeriesService} from "../../../../core/services/series.service";
import {MessageService} from "primeng/api";
import {noWhiteSpaceValidator} from "../../../../shared/validators/no-white-space.validator";
import {CreateSeries, Series, SeriesListPost, UpdateSeries} from "../../../../core/types/series.type";
import slugify from "slugify";
import {PostList} from "../../../../core/types/post.type";
import {PostService} from "../../../../core/services/post.service";
import {LoadingService} from "../../../../core/services/loading.service";
import {Subject, takeUntil} from "rxjs";
import {capitalizeFirstLetter} from "../../../../shared/commons/shared";

@Component({
  selector: 'main-edit-series',
  templateUrl: './edit-series.component.html',
  styleUrls: ['./edit-series.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class EditSeriesComponent implements OnInit,OnDestroy{

  isLoading:boolean = false

  editSeriesForm:FormGroup
  addPostToSeriesForm:FormGroup

  addPostToSeriesVisible: boolean = false

  series:SeriesListPost
  listPost:PostList[] = []
  listPostAdd:PostList[] = []

  destroy$ = new Subject<void>()


  slug:string
  constructor(
      private router:Router,
      private _router:ActivatedRoute,
      private fb:FormBuilder,
      private seriesService:SeriesService,
      private postService:PostService,
      private message:MessageService,
      public loadingService:LoadingService
  ) {
  }

  ngOnInit() {
    this.editSeriesForm = this.fb.group({
      title:[
        '',
        Validators.compose([
          Validators.required,
          Validators.maxLength(100),
          noWhiteSpaceValidator()
        ])
      ],
      content:[
        '',
        Validators.compose([
          Validators.required,
          noWhiteSpaceValidator()
        ])
      ]
    })

    this.addPostToSeriesForm = this.fb.group({
      posts:[
        '',
        Validators.compose([
          Validators.required
        ])
      ],
    })

    this._router.paramMap.pipe(takeUntil(this.destroy$)).subscribe((params) =>{
      if(!params.get("slug")){
        this.router.navigate(["/nguoi-dung/quan-ly-series"])
      }
      this.slug = params.get("slug") as string
    })

    this.loadingService.startLoading()
    this.seriesService.getSeriesDetailByAuth(this.slug).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        const data = response.data
        this.series = data
        this.listPost = data.posts
        this.editSeriesForm.get("title")?.setValue(data.title)
        this.editSeriesForm.get("content")?.setValue(data.content)

        this.loadingService.stopLoading()
      },
      error:(error) =>{
        this.message.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.router.navigate(["/"])
        this.loadingService.stopLoading()
      }
    })
  }

  onEditSeries(){
    const formValue = this.editSeriesForm.value

    const data:UpdateSeries = {
      content: formValue.content,
      title: capitalizeFirstLetter(formValue.title),
      slug: slugify(formValue.title.toLowerCase())
    }
    this.isLoading = true
    this.seriesService.updateSeries(data,this.series.id).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.message.add({
          severity:"success",
          detail:response.message,
          summary:"Thành Công"
        })
        this.isLoading = false
        this.router.navigate(["/nguoi-dung/quan-ly-series"])
      },
      error:(error) =>{
        this.message.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.isLoading = false
      }
    })
  }

  onRemovePost(seriesId:number,postId:number){
    this.isLoading = true
    this.postService.removePostToSeries(postId,seriesId).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) => {
        this.isLoading = false
        this.message.add({
          severity:"success",
          detail:response.message,
          summary:"Thành Công"
        })
        this.listPost = this.listPost.filter((item:PostList) => item.id !== postId)
      },
      error:(error) =>{
        this.message.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.isLoading = false
      }
    })
  }

  onAddPostToSeries(seriesId:number){
    const postSelected = this.addPostToSeriesForm.value.posts
    this.isLoading = true
    this.postService.addPostToSeries(postSelected.id,seriesId).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.message.add({
          severity:"success",
          detail:response.message,
          summary:"Thành Công"
        })
        const postAdded = this.listPostAdd.filter((item:PostList) => item.id === postSelected.id)
        this.listPostAdd = this.listPostAdd.filter((item:PostList) => item.id !== postSelected.id)
        this.listPost = [...postAdded,...this.listPost]
        this.isLoading = false
        this.addPostToSeriesVisible = false
      },
      error:(error) =>{
        this.message.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.isLoading = false
      }
    })
  }

  onShow(){
    this.isLoading = true
    this.postService.getAllPostNotBeLongSeries().pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.listPostAdd = response.data
        this.isLoading = false
        this.addPostToSeriesVisible = true
      },
      error:(error) =>{
        this.message.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.isLoading = false
      }
    })
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
