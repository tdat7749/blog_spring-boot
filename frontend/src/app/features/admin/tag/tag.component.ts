import {Component, ViewEncapsulation} from '@angular/core';
import {combineLatest, debounceTime, distinctUntilChanged, Observable, Subject, switchMap, takeUntil, tap} from "rxjs";
import {SortBy} from "../../../core/types/api-response.type";
import {ConfirmationService, MenuItem, MessageService} from "primeng/api";
import {LoadingService} from "../../../core/services/loading.service";
import {PaginationService} from "../../../core/services/pagination.service";
import {TagService} from "../../../core/services/tag.service";
import {CreateTag, Tag, UpdateTag} from "../../../core/types/tag.type";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {noWhiteSpaceValidator} from "../../../shared/validators/no-white-space.validator";
import {hasSpecialCharacters} from "../../../shared/validators/has-special-characters.validator";
import {capitalizeFirstLetter} from "../../../shared/commons/shared";
import slugify from "slugify";

@Component({
  selector: 'app-admin-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TagComponent {
  isLoading: boolean = false
  isGetLoading: boolean = false

  listTag: Tag[] = []
  totalPage: number


  search$: Observable<[number, string, SortBy]>

  destroy$ = new Subject<void>()

  items: MenuItem[] | undefined;

  activeItem: MenuItem | undefined;

  createTagForm:FormGroup
  createTagFormVisible:boolean = false

  updateTagForm:FormGroup
  updateTagFormVisible:boolean = false

  constructor(
      private tagService:TagService,
      private messageService:MessageService,
      private confirmService:ConfirmationService,
      public loadingService:LoadingService,
      private paginationService:PaginationService,
      private fb:FormBuilder
  ) {
  }

  ngOnInit() {
    this.createTagForm = this.fb.group({
      title:[
          '',
          Validators.compose([
              Validators.required,
              Validators.maxLength(100),
              noWhiteSpaceValidator()
          ])
      ]
    })

    this.updateTagForm = this.fb.group({
      title:[
        '',
        Validators.compose([
          Validators.required,
          Validators.maxLength(100),
          noWhiteSpaceValidator()
        ])
      ],
      id:[
          ''
      ]
    })

    this.items = [
      { label: 'Tất Cả', icon: 'pi pi-fw pi-book' }
    ];
    this.activeItem = this.items[0];
    this.getAllTag()
  }

  getAllTag(){
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
          return this.tagService.getListTag(pageIndex, keyword, sortBy)
        })
    ).subscribe({
      next: (response) => {
        this.totalPage = response.data.totalPage
        this.listTag = response.data.data
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

  deleteTag(tag:Tag,event:Event){
    this.confirmService.confirm({
      target:event.target as EventTarget,
      message:`Bạn có chắc chắn muốn xóa tag này ?`,
      header:"Xóa Tag",
      icon:"pi pi-exclamation-triangle",
      accept:() => {
        this.isLoading = true
        this.tagService.deleteTag(tag.id).pipe(takeUntil(this.destroy$)).subscribe({
          next:(response) => {
            this.messageService.add({
              severity:"success",
              detail:response.message,
              summary:"Thành Công"
            })
            this.listTag = this.listTag.filter((item) => item.id !== tag.id)
            this.isLoading = false
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
      },
      reject:() => {
        this.messageService.add({ severity: 'info', summary: 'Thông Báo', detail: 'Bạn đã hủy việc xóa tag' });
      }
    })
  }

  onCreateTag(){
    const formValue = this.createTagForm.value

    const data:CreateTag = {
      title: capitalizeFirstLetter(formValue.title),
      slug: slugify(formValue.title.toLowerCase())
    }

    if(hasSpecialCharacters(data.title)){
      this.messageService.add({
        severity:"error",
        detail:"Tiêu đề có chứa ký tự đặc biệt",
        summary:"Lỗi"
      })
      return;
    }

    if(this.createTagForm.invalid){
      this.messageService.add({
        severity:"error",
        detail:"Vui lòng nhập liệu đúng yêu cầu",
        summary:"Lỗi"
      })
      return;
    }

    this.isLoading = true
    this.tagService.createTag(data).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) => {
        this.messageService.add({
          severity:"success",
          detail:response.message,
          summary:"Thành Công"
        })
        const data:Tag = {
          title: response.data.title,
          createdAt: response.data.createdAt,
          updatedAt: response.data.updatedAt,
          slug: response.data.slug,
          id: response.data.id,
        }
        this.listTag = [data,...this.listTag]
        this.createTagFormVisible = false
        this.isLoading = false
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

  onUpdateTag(){
    const formValue = this.updateTagForm.value

    const data:UpdateTag = {
      title: capitalizeFirstLetter(formValue.title),
      slug: slugify(formValue.title.toLowerCase())
    }

    if(hasSpecialCharacters(data.title)){
      this.messageService.add({
        severity:"error",
        detail:"Tiêu đề có chứa ký tự đặc biệt",
        summary:"Lỗi"
      })
      return;
    }

    if(this.updateTagForm.invalid){
      this.messageService.add({
        severity:"error",
        detail:"Vui lòng nhập liệu đúng yêu cầu",
        summary:"Lỗi"
      })
      return;
    }

    this.isLoading = true
    this.tagService.updateTag(data,formValue.id).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) => {
        this.messageService.add({
          severity:"success",
          detail:response.message,
          summary:"Thành Công"
        })
        this.listTag = this.listTag.map((item) => {
          if(item.id === formValue.id){
            return {
              ...item,
              slug: response.data.slug,
              title:response.data.title
            }
          }
          return item
        })
        this.updateTagFormVisible = false
        this.isLoading = false
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

  onVisibleCreateTagForm(){
    this.createTagFormVisible = true
  }

  onVisibleUpdateTagForm(tag:Tag){
    this.updateTagForm.get("title")?.setValue(tag.title)
    this.updateTagForm.get("id")?.setValue(tag.id)

    this.updateTagFormVisible = true
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
