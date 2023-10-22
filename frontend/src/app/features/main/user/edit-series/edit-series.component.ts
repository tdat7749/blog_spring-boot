import {Component, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {SeriesService} from "../../../../core/services/series.service";
import {MessageService} from "primeng/api";
import {noWhiteSpaceValidator} from "../../../../shared/validators/no-white-space.validator";
import {CreateSeries, Series, SeriesListPost, UpdateSeries} from "../../../../core/types/series.type";
import slugify from "slugify";
import {PostList} from "../../../../core/types/post.type";
import {PostService} from "../../../../core/services/post.service";

@Component({
  selector: 'main-edit-series',
  templateUrl: './edit-series.component.html',
  styleUrls: ['./edit-series.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class EditSeriesComponent {

  isLoading:boolean = false

  editSeriesForm:FormGroup
  addPostToSeriesForm:FormGroup

  addPostToSeriesVisible: boolean = false

  series:SeriesListPost
  listPost:PostList[] = []
  listPostAdd:PostList[] = []

  slug:string
  constructor(
      private router:Router,
      private _router:ActivatedRoute,
      private fb:FormBuilder,
      private seriesService:SeriesService,
      private postService:PostService,
      private message:MessageService) {
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

    this._router.paramMap.subscribe((params) =>{
      if(!params.get("slug")){
        this.router.navigate(["/nguoi-dung/quan-ly-series"])
      }
      this.slug = params.get("slug") as string
    })

    this.seriesService.getSeriesDetailByAuth(this.slug).subscribe({
      next:(response) =>{
        const data = response.data
        this.series = data
        this.listPost = data.posts
        this.editSeriesForm.get("title")?.setValue(data.title)
        this.editSeriesForm.get("content")?.setValue(data.content)
      },
      error:(error) =>{
        this.message.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.isLoading = false
        this.router.navigate(["/"])
      }
    })
  }

  onEditSeries(){
    const formValue = this.editSeriesForm.value

    const data:UpdateSeries = {
      content: formValue.content,
      title: formValue.title,
      slug: slugify(formValue.title.toLowerCase())
    }
    this.isLoading = true
    this.seriesService.updateSeries(data,this.series.id).subscribe({
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
    this.postService.removePostToSeries(postId,seriesId).subscribe({
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
    console.log(postSelected)
    this.isLoading = true
    this.postService.addPostToSeries(postSelected.id,seriesId).subscribe({
      next:(response) =>{
        this.message.add({
          severity:"success",
          detail:response.message,
          summary:"Thành Công"
        })
        const postAdded = this.listPostAdd.filter((item:PostList) => item.id === postSelected.id)
        this.listPostAdd = this.listPostAdd.filter((item:PostList) => item.id !== postSelected.id)
        this.listPost = [...postAdded,...this.listPost]
        console.log(this.listPost)
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
    this.postService.getAllPostNotBeLongSeries().subscribe({
      next:(response) =>{
        console.log(response)
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
}
