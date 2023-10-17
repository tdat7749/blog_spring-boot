import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {PostService} from "../../../../core/services/post.service";
import {BehaviorSubject, combineLatest, debounceTime, distinctUntilChanged, switchMap} from "rxjs";
import {SortBy} from "../../../../core/types/api-response.type";
import {MessageService} from "primeng/api";
import {PostList} from "../../../../core/types/post.type";

@Component({
  selector: 'main-post-management',
  templateUrl: './post-management.component.html',
  styleUrls: ['./post-management.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PostManagementComponent implements OnInit{

  isLoading:boolean = false
  listPost:PostList[]
  totalPage:number
  totalRecord:number

  pageIndex = new BehaviorSubject<number>(1)
  keyword = new BehaviorSubject<string | unknown>(undefined)
  sortBy = new BehaviorSubject<SortBy>("createdAt")

  search$ = combineLatest([this.pageIndex,this.keyword,this.sortBy])

  constructor(private postService:PostService,private messageService:MessageService) {

  }

  ngOnInit() {
    this.isLoading = true
    this.search$.pipe(
        debounceTime(700),
        distinctUntilChanged(), // chỉ gọi lại khi có giá trị thay đổi
        switchMap(([pageIndex,keyword,sortBy]) =>{
          return this.postService.getAllPostByCurrentUser(pageIndex,sortBy)
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
    // this.keyword.next(event.)
    console.log(event)
  }

  onChangePageIndex(pageIndex:number){
    this.pageIndex.next(pageIndex)
  }

  onChangeSort(sortBy:SortBy){
    this.sortBy.next(sortBy)
  }

}
