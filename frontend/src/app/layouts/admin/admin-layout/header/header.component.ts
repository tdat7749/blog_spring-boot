import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {User} from "../../../../core/types/user.type";
import {RpNotification} from "../../../../core/types/noti.type";
import {Subject, takeUntil} from "rxjs";
import {SideBarComponent} from "../../../main/main-layout/header/side-bar/side-bar.component";
import {AuthService} from "../../../../core/services/auth.service";
import {UserService} from "../../../../core/services/user.service";
import {MessageService} from "primeng/api";
import {LocalStorageService} from "../../../../core/services/local-storage.service";
import {WebSocketService} from "../../../../core/services/websocket.service";

@Component({
  selector: 'app-admin-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class HeaderComponent {
  userInfo: User | null = null
  destroy$ = new Subject<void>()

  @ViewChild("sideBar") sideBar: SideBarComponent | undefined

  constructor(private authService: AuthService,
              private userService: UserService,
              private messageService: MessageService,
              private localStorageService: LocalStorageService,
              private webSocketService: WebSocketService
  ) {

  }

  ngOnInit() {
    this.authService.userState$.pipe(takeUntil(this.destroy$)).subscribe(response => {
      this.userInfo = response
    })
  }

  logout() {
    this.authService.logout().pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (response) => {
            this.localStorageService.remove("refreshToken")
            this.localStorageService.remove("accessToken")
            this.authService.userState$.next(null)
            this.userService.notificationState$.next(null)
            this.webSocketService.onDisconnect()
            this.messageService.add({
              severity: "success",
              detail: "Đăng xuất thành công",
              summary: "Thành Công"
            })
          },
          error: (error) => {
            this.messageService.add({
              severity: "error",
              detail: error,
              summary: "Lỗi"
            })
          }
        })
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
