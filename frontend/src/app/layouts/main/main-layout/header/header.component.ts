import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {AuthService} from "../../../../core/services/auth.service";
import {User} from "../../../../core/types/user.type";
import {listNav} from "../../../../shared/commons/shared";
import {SideBarComponent} from "./side-bar/side-bar.component";
import {MessageService} from "primeng/api";
import {UserService} from "../../../../core/services/user.service";
import {Subject, takeUntil} from "rxjs";
import {RpNotification} from "../../../../core/types/noti.type";
import {LocalStorageService} from "../../../../core/services/local-storage.service";

@Component({
  selector: 'main-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy{
  userInfo: User | null = null

  listNav=listNav
  rpNotification:RpNotification | null
  notificationVisible:boolean = false

  destroy$ = new Subject<void>()

  @ViewChild("sideBar") sideBar: SideBarComponent | undefined

  constructor(private authService: AuthService,
              private userService:UserService,
              private messageService:MessageService,
              private localStorageService:LocalStorageService,
  ) {

  }

  ngOnInit() {
    this.authService.userState$.pipe(takeUntil(this.destroy$)).subscribe(response => {
      this.userInfo = response
    })

    this.userService.notificationState$.pipe(takeUntil(this.destroy$)).subscribe(response => {
      this.rpNotification = response
    })
  }

  logout() {
    this.authService.logout().pipe(takeUntil(this.destroy$))
        .subscribe({
          next:(response) => {
            this.localStorageService.remove("refreshToken")
            this.localStorageService.remove("accessToken")
            this.authService.userState$.next(null)
            this.userService.notificationState$.next(null)
            this.messageService.add({
              severity:"success",
              detail:"Đăng xuất thành công",
              summary:"Thành Công"
            })
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

  openSideBar(){
    if(this.sideBar){
      this.sideBar.isOpen = true
    }
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }

  openNotificationDialog(){
    this.notificationVisible = true
  }

}
