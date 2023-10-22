import {AfterViewInit, Component, OnInit, ViewChild, ViewChildren, ViewEncapsulation} from '@angular/core';
import {PostService} from "../../../../core/services/post.service";
import {BehaviorSubject, combineLatest, debounceTime, distinctUntilChanged, Observable, switchMap, tap} from "rxjs";
import {SortBy} from "../../../../core/types/api-response.type";
import {MessageService} from "primeng/api";
import {PostList} from "../../../../core/types/post.type";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {noWhiteSpaceValidator} from "../../../../shared/validators/no-white-space.validator";
import {Editor} from "primeng/editor";
import {FileUpload} from "primeng/fileupload";
import {MAX_FILE, MIME_TYPES} from "../../../../shared/commons/shared";
import {PaginationService} from "../../../../core/services/pagination.service";

@Component({
  selector: 'main-post-management',
  templateUrl: './post-management.component.html',
  styleUrls: ['./post-management.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PostManagementComponent implements OnInit{

  isLoading:boolean = false

  listPost:PostList[] = []
  totalPage:number
  totalRecord:number


  search$:Observable<[number,string,SortBy]>


  constructor(private paginationService:PaginationService,private postService:PostService,private messageService:MessageService,private fb:FormBuilder) {

  }

  ngOnInit() {
    this.isLoading = true
    this.search$ = combineLatest([
        this.paginationService.pageIndex$,
        this.paginationService.keyword$,
        this.paginationService.sortBy$
    ])
    this.search$.pipe(
        tap(() => this.isLoading = true),
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
        this.isLoading = false
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


  onChangeSearch(event:any){
    this.paginationService.updateKeyword(event.target.value)
  }

  onChangePageIndex(event: any){
      this.paginationService.updatePageIndex(event.page)
  }

  onChangeSort(sortBy:SortBy){
    this.paginationService.updateSortBy(sortBy)
  }
}
