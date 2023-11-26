import { Component, OnDestroy, OnInit, ViewEncapsulation } from "@angular/core";
import { combineLatest, debounceTime, distinctUntilChanged, Observable, Subject, switchMap, takeUntil, tap } from "rxjs";
import { SortBy } from "../../../core/types/api-response.type";
import { MessageService } from "primeng/api";
import { LoadingService } from "../../../core/services/loading.service";
import { PaginationService } from "../../../core/services/pagination.service";
import { PostList } from "../../../core/types/post.type";
import { PostService } from "../../../core/services/post.service";
import {SelectPathService} from "../../../core/services/select-path.service";
import {ActivatedRoute} from "@angular/router";


interface SortSelect{
    title: string,
    value: string
}
@Component({
    selector: 'main-post',
    templateUrl: './post.component.html',
    encapsulation: ViewEncapsulation.None
})



export class PostComponent implements OnInit, OnDestroy {
    search$: Observable<[number, string, SortBy]>

    totalPage: number
    listPost: PostList[] = []

    destroy$ = new Subject<void>()

    sorts: SortSelect[] | undefined;

    sortValue: SortSelect | undefined;

    constructor(
        private postService: PostService,
        private messageService: MessageService,
        public loadingService: LoadingService,
        private paginationService: PaginationService,
        private selectPathService: SelectPathService,
        private _router:ActivatedRoute
    ) {

    }

    ngOnInit() {
        this._router.parent?.url.pipe(takeUntil(this.destroy$)).subscribe(url => {
            this.selectPathService.path$.next(url[0].path)
        })
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
                return this.postService.getAllPostPublished(keyword, pageIndex, sortBy)
            })
        ).subscribe({
            next: (response) => {
                this.totalPage = response.data.totalPage
                this.listPost = response.data.data
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
        this.sorts = [
            {
                title:"Mới Nhất",
                value:"newest"
            },
            {
                title:"Cũ Nhất",
                value:"latest"
            },
            {
                title:"Lượt Xem",
                value:"totalView"
            }
        ]

    }

    onChangeSearch(event: any) {
        this.paginationService.updateKeyword(event.target.value)
    }

    onChangePageIndex(event: any) {
        this.paginationService.updatePageIndex(event.page)
    }

    onChangeSort(event:any){
        this.paginationService.updateSortBy(event.value.value)
    }

    ngOnDestroy() {
        this.selectPathService.path$.next("")
        this.paginationService.onDestroy()
        this.destroy$.next()
        this.destroy$.complete()
    }
}