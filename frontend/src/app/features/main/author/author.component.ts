import {Component, Input, OnInit, ViewEncapsulation} from "@angular/core";
import {User} from "../../../core/types/user.type";
import {UserService} from "../../../core/services/user.service";
import {combineLatest, debounceTime, distinctUntilChanged, forkJoin, Observable, switchMap, tap} from "rxjs";
import {PostService} from "../../../core/services/post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {PaginationService} from "../../../core/services/pagination.service";
import {SortBy} from "../../../core/types/api-response.type";
import {PostList} from "../../../core/types/post.type";

@Component({
    selector:"main-author",
    templateUrl:"./author.component.html",
    encapsulation: ViewEncapsulation.None,
    styleUrls:["./author.component.css"]
})

export class AuthorComponent implements OnInit{
    isLoading:boolean = false

    author:User
    userName:string

    totalPage:number
    search$:Observable<[number,string,SortBy]>

    listPost:PostList[] = []

    constructor(
        private _router:ActivatedRoute,
        private userService:UserService,
        private postService:PostService,
        private messageService:MessageService,
        private paginationService:PaginationService,
        private router:Router
    ) {

    }

    ngOnInit() {
        this._router.paramMap.subscribe(params => {
            this.userName = params.get("userName") as string
        })

        this.userService.getAuthor(this.userName).subscribe({
            next:(response) =>{
                this.author = response.data
                this.isLoading = false
            },
            error:(error) => {
                this.messageService.add({
                    severity:"error",
                    detail:error,
                    summary:"Lỗi"
                })
                this.router.navigate(['/'])
                return;
            }
        })

        this.search$ = combineLatest([
            this.paginationService.pageIndex$,
            this.paginationService.keyword$,
            this.paginationService.sortBy$
        ])

        this.search$.pipe(
            tap(() => this.isLoading = true),
            debounceTime(700),
            distinctUntilChanged(),
            switchMap(([pageIndex,keyword,sortBy]) => {
                return this.postService.getAllPostOfAuthorByUserName(keyword,this.userName,pageIndex,sortBy)
            })
        ).subscribe({
            next:(response) => {
                this.listPost = response.data.data
                this.totalPage = response.data.totalPage
                console.log(response.data.data)
            },
            error:(error) => {
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

}