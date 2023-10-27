import { Component, OnInit } from '@angular/core';
import { AuthService } from "./core/services/auth.service";
import { CookieService } from "ngx-cookie-service";
import { MessageService } from "primeng/api";
import { concatMap, map, switchMap } from "rxjs";
import { UserService } from "./core/services/user.service";
import { LocalStorageService } from "./core/services/local-storage.service";
import { WebSocketService } from './core/services/websocket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private userService: UserService,
    private localStorageService: LocalStorageService,
    private messageService: MessageService,
    private webSocketService: WebSocketService
  ) {

  }
  title = 'bố thăng docker rách';

  ngOnInit() {
    if (this.localStorageService.get("accessToken") !== null && this.localStorageService.get("refreshToken") !== "" && this.authService.getCurrentUser() === null) {
      this.authService.getMe().pipe(
        switchMap((getMeResponse) => {
          return this.userService.getTop10Notification().pipe(
            map(notificationResponse => {
              return { getMeResponse, notificationResponse }
            })
          )
        })
      ).subscribe({
        next: (response) => {
          this.authService.setCurrentUser(response.getMeResponse.data)
          this.authService.userState$.next(response.getMeResponse.data)
          this.userService.notificationState$.next(response.notificationResponse.data)
          this.webSocketService.onConnect(response.getMeResponse.data.userName as string)
          console.log(response.getMeResponse.data.userName)
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
  }
}
