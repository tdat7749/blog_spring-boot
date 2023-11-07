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
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Series } from "../../../../core/types/series.type";
import { CreateTag, Tag } from "../../../../core/types/tag.type";
import { Editor } from "primeng/editor";
import { FileUpload } from "primeng/fileupload";
import { PostService } from "../../../../core/services/post.service";
import { FileStorageService } from "../../../../core/services/file-storage.service";
import { MessageService } from "primeng/api";
import { SeriesService } from "../../../../core/services/series.service";
import { TagService } from "../../../../core/services/tag.service";
import { ActivatedRoute, Router } from "@angular/router";
import { forkJoin, Subject, takeUntil } from "rxjs";
import { noWhiteSpaceValidator } from "../../../../shared/validators/no-white-space.validator";
import { fileUploadValidator } from "../../../../shared/validators/file-upload.validator";
import Delta from "quill-delta";
import { Post, TOC, UpdatePost } from "../../../../core/types/post.type";
import { capitalizeFirstLetter, getNewTagByString, postStatus } from "../../../../shared/commons/shared";
import slugify from "slugify";
import { LoadingService } from "../../../../core/services/loading.service";
import { generateTOC } from 'src/app/shared/commons/generate-toc';

@Component({
  selector: 'main-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class EditPostComponent implements OnInit, AfterViewInit, OnDestroy {

  editPostForm: FormGroup
  previewImage: string = ""
  isLoading: boolean = false

  post: Post
  listSeries: Series[]
  listTag: Tag[]
  imageFile: File | null

  destroy$ = new Subject<void>()

  postStatus = postStatus

  @ViewChildren(Editor) editors: QueryList<Editor>;
  @ViewChild("fileUpload") fileUpload: FileUpload

  isUploadImageLoading: boolean = false
  previewDialogVisible: boolean = false
  previewPost: { title: string, content: string }
  toc: TOC[]

  slug: string

  constructor(
    private postService: PostService,
    private fileStorageService: FileStorageService,
    private fb: FormBuilder,
    private messageService: MessageService,
    private seriesService: SeriesService,
    private tagService: TagService,
    private router: Router,
    private _router: ActivatedRoute,
    public loadingService: LoadingService
  ) { }

  ngOnInit() {

    this.previewPost = { title: "Chỗ này là title", content: "<p>Đây là content</p>" }

    this.editPostForm = this.fb.group({
      title: [
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(100),
          noWhiteSpaceValidator()
        ])
      ],
      content: [
        '',
        Validators.compose([
          Validators.required,
        ])
      ],
      summary: [
        '',
        Validators.compose([
          Validators.required,
        ])
      ],
      tags: [
      ],
      series: [
      ],
      newTag: [
        ''
      ]
    })


    this._router.paramMap.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      this.slug = params.get("slug") as string;
    })

    forkJoin([
      this.tagService.getAllTag(),
      this.seriesService.getSeriesByCurrentUser(),
      this.postService.getPostDetailBySlugAuth(this.slug)
    ],
      (tagResponse, seriesResponse, postResponse) => {
        return {
          listTag: tagResponse.data,
          listSeries: seriesResponse.data,
          post: postResponse.data
        }
      }
    ).pipe(takeUntil(this.destroy$)).subscribe({
      next: (response) => {
        this.listTag = response.listTag
        this.listSeries = response.listSeries
        this.post = response.post

        this.editPostForm.patchValue({
          title: this.post.title,
          content: this.post.content,
          summary: this.post.summary,
        })

        this.previewImage = this.post.thumbnail

        if (this.post.series !== null) {
          this.editPostForm.get("series")?.setValue(this.post.series)
        }
        this.editPostForm.get("isPublished")?.setValue({ status: this.post.isPublished })
        this.editPostForm.get("tags")?.setValue(this.post.tags)
      },
      error: (error) => {
        this.messageService.add({
          severity: "error",
          detail: error,
          summary: "Lỗi"
        })
        this.router.navigate(["/"])
        return;
      }
    })
  }

  ngAfterViewInit() {
    this.editors.map(editor => {
      const quill = editor.getQuill();
      quill.root.setAttribute("spellcheck",false)
      const toolbar = quill.getModule('toolbar');

      toolbar.addHandler('image', () => {
        let fileInput = toolbar.container.querySelector('button.ql-image');
        fileInput = document.createElement("input")
        fileInput.setAttribute("type", 'file')
        fileInput.click()
        fileInput.addEventListener('change', () => {
          const file = fileInput.files[0]
          const result = fileUploadValidator(file)
          if (!result.success) {
            this.messageService.add({
              severity: "error",
              detail: result.message,
              summary: "Lỗi"
            })
            return;
          }

          let formData = new FormData()
          formData.set("file", file)
          this.isUploadImageLoading = true
          this.fileStorageService.upload(formData).subscribe({
            next: (response) => {
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
              this.isUploadImageLoading = false
            },
            error: (error) => {
              this.isUploadImageLoading = false
              this.messageService.add({
                severity: "error",
                detail: error,
                summary: "Lỗi"
              })
            }
          })
        })
      })
    })
  }

  onUpdatePost() {
    const formValue = this.editPostForm.value
    let createListTagDTO: CreateTag[] = formValue.tags.map((tag: Tag) => {
      let createTagDTO: CreateTag = {
        title: capitalizeFirstLetter(tag.title),
        slug: tag.slug
      }
      return createTagDTO
    })

    const getNewTag = getNewTagByString(formValue.newTag)
    if (!getNewTag.success) {
      this.messageService.add({
        severity: "error",
        detail: "Tag mới có kí tự đặc biệt",
        summary: "Lỗi"
      })
      return
    }
    if (getNewTag.data !== null) {
      createListTagDTO = [...createListTagDTO, ...getNewTag.data]
    }

    if (createListTagDTO?.length > 3 || createListTagDTO.length < 1) {
      this.messageService.add({
        severity: "error",
        detail: "Bài viết phải có tối thiểu 1 hoặc tối đa 3 tag",
        summary: "Lỗi"
      })
      return;
    }

    const data: UpdatePost = {
      title: capitalizeFirstLetter(formValue.title),
      content: formValue.content,
      summary: formValue.summary,
      slug: slugify(formValue.title.toLowerCase()),
      listTags: createListTagDTO
    }

    if (formValue.series !== null) {
      data.seriesId = formValue.series.id
    }


    this.isLoading = true
    if (this.imageFile) {
      const formData = new FormData()
      formData.set("file", this.imageFile)
      this.isLoading = true
      this.fileStorageService.upload(formData).pipe(takeUntil(this.destroy$)).subscribe({
        next: (response) => {
          this.isLoading = false
          data.thumbnail = response.data
        },
        error: (error) => {
          this.isLoading = false
          this.messageService.add({
            severity: "error",
            detail: error,
            summary: "Lỗi"
          })
        }
      })
    }
    this.isLoading = true


    this.postService.updatePost(data, this.post.id).pipe(takeUntil(this.destroy$)).subscribe({
      next: (response) => {
        this.isLoading = false
        this.messageService.add({
          severity: "success",
          detail: response.message,
          summary: "Thành công"
        })
        this.router.navigate(['/nguoi-dung/thong-tin'])
      },
      error: (error) => {
        this.isLoading = false
        this.messageService.add({
          severity: "error",
          detail: error,
          summary: "Lỗi"
        })
      }
    })
  }

  onSelectImage(event: any) {
    const file = event.files[0]
    if (file) {
      const result = fileUploadValidator(file)
      if (!result.success) {
        this.messageService.add({
          severity: "error",
          detail: result.message,
          summary: "Lỗi"
        })
        return;
      }
      this.previewImage = file?.objectURL.changingThisBreaksApplicationSecurity
      this.imageFile = file
    }
  }

  onClearUpload() {
    this.previewImage = this.post.thumbnail
    this.imageFile = null
    this.fileUpload.clear()
  }

  onPreviewDialogVisible() {
    const formValue = this.editPostForm.value
    this.previewPost.content = formValue.content || ""
    this.previewPost.title = formValue.title || ""
    this.toc = generateTOC(formValue.content) || []

    this.previewDialogVisible = true
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }

}
