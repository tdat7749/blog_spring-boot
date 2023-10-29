import {Component, ViewEncapsulation} from '@angular/core';
import {combineLatest, debounceTime, distinctUntilChanged, Observable, Subject, switchMap, takeUntil, tap} from "rxjs";
import {SortBy} from "../../../core/types/api-response.type";
import {ConfirmationService, MenuItem, MessageService} from "primeng/api";
import {LoadingService} from "../../../core/services/loading.service";
import {PaginationService} from "../../../core/services/pagination.service";
import {ChangePermission, Role, User} from "../../../core/types/user.type";
import {UserService} from "../../../core/services/user.service";
import {AuthService} from "../../../core/services/auth.service";

@Component({
  selector: 'app-admin-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserComponent {
  isLoading: boolean = false
  isGetLoading: boolean = false

  listUser: User[] = []
  totalPage: number


  search$: Observable<[number, string, SortBy]>

  destroy$ = new Subject<void>()

  items: MenuItem[] | undefined;

  activeItem: MenuItem | undefined;


  constructor(
      private userService:UserService,
      private messageService:MessageService,
      private confirmService:ConfirmationService,
      public loadingService:LoadingService,
      private paginationService:PaginationService,
      private authService:AuthService
  ) {
  }

  ngOnInit() {
    this.items = [
      { label: 'Tất Cả', icon: 'pi pi-fw pi-book' }
    ];
    this.activeItem = this.items[0];
    this.getAllUser()
  }

  getAllUser(){
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
          return this.userService.getAllUser(keyword, pageIndex, sortBy)
        })
    ).subscribe({
      next: (response) => {
        this.totalPage = response.data.totalPage
        this.listUser = response.data.data
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

  unLockAccount(user:User){
    this.isLoading = true
    this.authService.unLockAccount(user.id as number).pipe(takeUntil(this.destroy$))
        .subscribe({
          next:(response) => {
            this.messageService.add({
              severity:"success",
              detail:response.message,
              summary:"Thành Công"
            })
            this.isLoading = false
            user.notLocked = true
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

  lockAccount(user:User){
    this.isLoading = true
    this.authService.lockAccount(user.id as number).pipe(takeUntil(this.destroy$))
        .subscribe({
          next:(response) => {
            this.messageService.add({
              severity:"success",
              detail:response.message,
              summary:"Thành Công"
            })
            this.isLoading = false
            user.notLocked = false
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

  changePermission(role:Role,user:User,event:Event){
    this.confirmService.confirm({
      target:event.target as EventTarget,
      message:`Bạn có chắc chắn muốn đổi quyền người dùng này thành ${role} ?`,
      header:"Đổi Quyền Người Dùng",
      icon:"pi pi-exclamation-triangle",
      accept:() => {
        this.isLoading = true
        const data:ChangePermission = {
          userId: user.id as number,
          role:role
        }
        this.userService.changePermission(data).pipe(takeUntil(this.destroy$))
            .subscribe({
              next:(response) => {
                this.messageService.add({
                  severity:"success",
                  detail:response.message,
                  summary:"Thành Công"
                })
                this.isLoading = false
                user.role = role
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
        this.messageService.add({ severity: 'info', summary: 'Thông Báo', detail: 'Bạn đã hủy việc đổi quyền' });
      }
    })
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
