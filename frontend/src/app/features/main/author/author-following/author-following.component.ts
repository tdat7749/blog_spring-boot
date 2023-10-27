import { Component, Input, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { combineLatest, debounceTime, distinctUntilChanged, Observable, Subject, switchMap, takeUntil, tap } from "rxjs";
import { SortBy } from "../../../../core/types/api-response.type";
import { PostList } from "../../../../core/types/post.type";
import { ActivatedRoute } from "@angular/router";
import { PostService } from "../../../../core/services/post.service";
import { MessageService } from "primeng/api";
import { PaginationService } from "../../../../core/services/pagination.service";
import { UserService } from "../../../../core/services/user.service";
import { User } from "../../../../core/types/user.type";
import { LoadingService } from "../../../../core/services/loading.service";

@Component({
  selector: 'main-author-following',
  templateUrl: './author-following.component.html',
  styleUrls: ['./author-following.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AuthorFollowingComponent implements OnInit, OnDestroy {
  isLoading: boolean = false
  isGetLoading: boolean = false

  userName: string

  totalPage: number
  search$: Observable<[number, SortBy]>

  listUser: User[] = []

  destroy$ = new Subject<void>()

  constructor(
    private _router: ActivatedRoute,
    private userService: UserService,
    private messageService: MessageService,
    private paginationService: PaginationService,
    public loadingService: LoadingService
  ) {

  }

  ngOnInit() {

    this._router.parent?.paramMap.pipe(takeUntil(this.destroy$)).subscribe(params => {
      this.userName = params.get("userName") as string
    })

    this.search$ = combineLatest([
      this.paginationService.pageIndex$,
      this.paginationService.sortBy$
    ]).pipe(takeUntil(this.destroy$))

    this.isGetLoading = true
    this.search$.pipe(
      takeUntil(this.destroy$),
      distinctUntilChanged(),
      switchMap(([pageIndex, sortBy]) => {
        return this.userService.getListFollowing(this.userName, pageIndex, sortBy)
      })
    ).subscribe({
      next: (response) => {
        this.totalPage = response.data.totalPage
        this.listUser = response.data.data
        this.isGetLoading = false
      },
      error: (error) => {
        this.messageService.add({
          severity: "error",
          detail: error,
          summary: "Lỗi"
        })
        this.isGetLoading = false
      }
    })
  }

  onChangePageIndex(event: any) {
    this.paginationService.updatePageIndex(event.page)
  }

  ngOnDestroy() {
    this.paginationService.onDestroy()
    this.destroy$.next()
    this.destroy$.complete()
  }
}
