import { Component, Input, OnDestroy, OnInit, ViewEncapsulation } from "@angular/core";
import { User } from "../../../core/types/user.type";
import { UserService } from "../../../core/services/user.service";
import {
    combineLatest,
    debounceTime,
    distinctUntilChanged,
    forkJoin,
    Observable,
    Subject,
    switchMap,
    takeUntil,
    tap
} from "rxjs";
import { PostService } from "../../../core/services/post.service";
import { ActivatedRoute, Router } from "@angular/router";
import { MenuItem, MessageService } from "primeng/api";
import { PaginationService } from "../../../core/services/pagination.service";
import { SortBy } from "../../../core/types/api-response.type";
import { PostList } from "../../../core/types/post.type";
import { LoadingService } from "../../../core/services/loading.service";

@Component({
    selector: "main-author",
    templateUrl: "./author.component.html",
    encapsulation: ViewEncapsulation.None,
    styleUrls: ["./author.component.css"]
})

export class AuthorComponent implements OnInit, OnDestroy {
    isLoading: boolean = false

    author: User
    userName: string

    items: MenuItem[] | undefined;

    activeItem: MenuItem | undefined;

    destroy$ = new Subject<void>()

    constructor(
        private _router: ActivatedRoute,
        private userService: UserService,
        private messageService: MessageService,
        private router: Router,
        public loadingService: LoadingService
    ) {
        this._router.paramMap.pipe(takeUntil(this.destroy$)).subscribe(params => {
            this.userName = params.get("userName") as string
            this.ngOnInit()
        })
    }

    ngOnInit() {
        this.loadingService.startLoading()
        this.userService.getAuthor(this.userName)
            .pipe(
                takeUntil(this.destroy$)
            )
            .subscribe({
                next: (response) => {
                    this.author = response.data
                    this.loadingService.stopLoading()
                },
                error: (error) => {
                    this.messageService.add({
                        severity: "error",
                        detail: error,
                        summary: "Lỗi"
                    })
                    this.loadingService.stopLoading()
                    this.router.navigate(['/'])
                    return;
                }
            })

        this.items = [
            { label: 'Bài Viết', icon: 'pi pi-fw pi-book', routerLink: `/tac-gia/${this.userName}` },
            { label: 'Đang Theo Dõi', icon: 'pi pi-fw pi-user-plus', routerLink: "dang-theo-doi" },
            { label: 'Người Theo Dõi', icon: 'pi pi-fw pi-users', routerLink: "nguoi-theo-doi" }
        ];

        this.activeItem = this.items[0];
    }

    onActiveItemChange(event: MenuItem) {
        this.activeItem = undefined
        this.activeItem = event;
    }

    ngOnDestroy() {
        this.destroy$.next()
        this.destroy$.complete()
    }
}