import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {combineLatest, Observable, Subject, switchMap, takeUntil, tap} from "rxjs";
import {PaginationService} from "../../../../core/services/pagination.service";
import {LoadingService} from "../../../../core/services/loading.service";
import {UserService} from "../../../../core/services/user.service";
import {MessageService} from "primeng/api";
import {Notification} from "../../../../core/types/noti.type";

@Component({
  selector: 'main-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css'],
  encapsulation:ViewEncapsulation.None
})
export class NotificationsComponent implements OnInit,OnDestroy{

  destroy$ = new Subject<void>()

  notifications:Notification[] = []
  unRead:number = 0
  totalPage:number

  isLoading:boolean = false

  paging$: Observable<[number]>

  constructor(
      private paginationService:PaginationService,
      public loadingService:LoadingService,
      private userService:UserService,
      private messageService:MessageService,

  ) {
  }

  ngOnInit() {

    this.paging$ = combineLatest([
        this.paginationService.pageIndex$
    ])
    this.paging$
        .pipe(
            tap(() => this.loadingService.startLoading()),
            takeUntil(this.destroy$),
            switchMap(([pageIndex]) => {
              return this.userService.getAllNotification(pageIndex)
            })
        ).subscribe({
          next:(response) => {
            this.totalPage = response.data.totalPage
            this.notifications = response.data.data.notifications
            this.unRead = response.data.data.unSeenNotification
            this.loadingService.stopLoading()
          },
          error:(error) => {
            this.messageService.add({
              severity:"error",
              detail:error,
              summary:"Lỗi"
            })
            this.loadingService.stopLoading()
          }
        })
  }

  onReadAll(){
    this.isLoading = true
    this.userService.readAllNotification().pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) => {
        this.messageService.add({
          severity:"success",
          detail:response.message,
          summary:"Thành Công"
        })
        this.notifications = this.notifications.map((item) => {
          return {
            ...item,
            read: true
          }
        })
        this.unRead = 0
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

  onChangePageIndex(event: any){
    this.paginationService.updatePageIndex(event.page)
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
