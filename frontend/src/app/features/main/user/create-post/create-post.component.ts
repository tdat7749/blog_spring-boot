import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  QueryList,
  ViewChild,
  ViewChildren,
  ViewEncapsulation
} from '@angular/core';
import {PostService} from "../../../../core/services/post.service";
import {FileStorageService} from "../../../../core/services/file-storage.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MessageService} from "primeng/api";
import {noWhiteSpaceValidator} from "../../../../shared/validators/no-white-space.validator";
import {Editor} from "primeng/editor";
import {FileUpload} from "primeng/fileupload";
import {fileUploadValidator} from "../../../../shared/validators/file-upload.validator";
import Delta from 'quill-delta';
import {concatMap, forkJoin, Subject, takeUntil} from "rxjs";
import {SeriesService} from "../../../../core/services/series.service";
import {TagService} from "../../../../core/services/tag.service";
import {Series} from "../../../../core/types/series.type";
import {CreateTag, Tag} from "../../../../core/types/tag.type";
import {CreatePost} from "../../../../core/types/post.type";
import slugify from "slugify";
import {capitalizeFirstLetter, getNewTagByString} from "../../../../shared/commons/shared";
import {Router} from "@angular/router";
import {LoadingService} from "../../../../core/services/loading.service";

@Component({
  selector: 'main-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CreatePostComponent implements OnInit,AfterViewInit,OnDestroy{

  createPostForm : FormGroup
  previewImage:string = ""
  isLoading:boolean = false

  listSeries:Series[]
  listTag:Tag[]
  imageFile: File | null

  destroy$ = new Subject<void>()


  @ViewChildren(Editor) editors: QueryList<Editor>;
  @ViewChild("fileUpload") fileUpload: FileUpload


  constructor(
      private postService:PostService,
      private fileStorageService:FileStorageService,
      private fb:FormBuilder,
      private messageService:MessageService,
      private seriesService:SeriesService,
      private tagService:TagService,
      private router:Router,
      public loadingService:LoadingService
  ) {

  }

  ngOnInit() {

    this.createPostForm = this.fb.group({
      title:[
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(100),
          noWhiteSpaceValidator()
        ])
      ],
      content:[
        '',
        Validators.compose([
          Validators.required,
        ])
      ],
      summary:[
        '',
        Validators.compose([
          Validators.required,
        ])
      ],
      tags:[],
      series:[
        null,
      ],
      newTag:[
        null
      ]
    })

    // this.loadingService.startLoading()
    forkJoin([
            this.tagService.getAllTag(),
            this.seriesService.getSeriesByCurrentUser()
        ],
        (tagResponse,seriesResponse) =>{
          return {
            listTag: tagResponse.data,
            listSeries:seriesResponse.data
          }
        }
    ).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.listTag = response.listTag
        this.listSeries = response.listSeries
        this.loadingService.stopLoading()
      },
      error:(error) =>{
        this.loadingService.stopLoading()
      }
    })

  }

  ngAfterViewInit() {
    this.editors.map(editor => {
      const quill = editor.getQuill();
      const toolbar = quill.getModule('toolbar');

      toolbar.addHandler('image', () => {
        let fileInput = toolbar.container.querySelector('button.ql-image');
        fileInput = document.createElement("input")
        fileInput.setAttribute("type",'file')
        fileInput.click()
        fileInput.addEventListener('change', () => {
          const file = fileInput.files[0]
          const result = fileUploadValidator(file)
          if(!result.success){
            this.messageService.add({
              severity: "error",
              detail: result.message,
              summary:"Lỗi"
            })
            return;
          }

          let formData = new FormData()
          formData.set("file",file)
          this.fileStorageService.upload(formData).pipe(takeUntil(this.destroy$)).subscribe({
            next:(response) =>{
              const range = quill.getSelection(true);
              quill.updateContents(
                  new Delta()
                      .retain(range.index)
                      .delete(range.length)
                      .insert({ image: response.data }),
                  'user'
              );
              quill.setSelection(range.index + 1, 'silent');
              fileInput.value = '';
            },
            error:(error) =>{
              this.messageService.add({
                severity:"error",
                detail:error,
                summary:"Lỗi"
              })
            }
          })
      })
    })
  })
}

  onCreatePost(){
    if(this.imageFile === null){
      this.messageService.add({
        severity:"error",
        detail:"Vui lòng chọn ảnh",
        summary:"Lỗi"
      })
      return;
    }
    const formData = new FormData()
    formData.set("file",this.imageFile)

    const formValue = this.createPostForm.value
    let createListTagDTO:CreateTag[] = formValue.tags?.map((tag:Tag) =>{
      let createTagDTO:CreateTag = {
        title: capitalizeFirstLetter(tag.title),
        slug: tag.slug
      }
      return createTagDTO
    }) || []

    const getNewTag = getNewTagByString(formValue.newTag)
    if(!getNewTag.success){
      this.messageService.add({
        severity:"error",
        detail:"Tag mới có kí tự đặc biệt",
        summary:"Lỗi"
      })
      return
    }
    if(getNewTag.data !== null){
      createListTagDTO = [...createListTagDTO,...getNewTag.data]
    }

    if(createListTagDTO?.length > 3 || createListTagDTO.length < 1){
      this.messageService.add({
        severity:"error",
        detail:"Bài viết phải có tối thiểu 1 hoặc tối đa 3 tag",
        summary:"Lỗi"
      })
      return;
    }

    this.isLoading = true
    this.fileStorageService.upload(formData).pipe(
        takeUntil(this.destroy$),
        concatMap((response) => {
          const data:CreatePost = {
            title: capitalizeFirstLetter(formValue.title),
            content: formValue.content,
            summary:formValue.summary,
            slug: slugify(formValue.title.toLowerCase()),
            thumbnail:response.data,
            listTags:createListTagDTO
          }
          if(formValue.series !== null){
            data.seriesId = formValue.series.id
          }

          return this.postService.createPost(data)
        })
    ).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.isLoading = false
        this.messageService.add({
          severity:"success",
          detail:response.message,
          summary:"Thành công"
        })
        this.router.navigate(["/nguoi-dung/quan-ly-bai-viet"])
      },
      error:(error) =>{
        this.isLoading = false
        this.messageService.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
      }
    })
  }

  onSelectImage(event: any){
    const file = event.files[0]
    if(file){
      const result = fileUploadValidator(file)
      if(!result.success){
        this.messageService.add({
          severity: "error",
          detail: result.message,
          summary:"Lỗi"
        })
        return;
      }
      this.previewImage = file?.objectURL.changingThisBreaksApplicationSecurity
      this.imageFile = file
    }
  }

  onClearUpload(){
    this.previewImage = ""
    this.imageFile = null
    this.fileUpload.clear()
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
