import { Injectable, OnInit } from "@angular/core";
import { Client, Message } from "@stomp/stompjs"
import { AuthService } from "./auth.service";
import { MessageService } from "primeng/api";
import { UserService } from "./user.service";
import { Notification, RpNotification } from "../types/noti.type";
import { take } from "rxjs";

@Injectable({
    providedIn: "root"
})

export class WebSocketService {
    stompClient: Client

    constructor(private authService: AuthService, private messageService: MessageService, private userService: UserService) {
        this.stompClient = new Client()

        this.authService.userState$.subscribe(response => {
            if (response !== null) {
                this.onConnect(response.userName as string)
            }
        })
    }

    onConnect(userName: string) {
        this.stompClient.configure({
            brokerURL: `ws://localhost:8081/ws/websocket`,
            onConnect: () => {
                this.stompClient.subscribe(`/user/${userName}/user/queue/notification`, (message) => {
                    const newMessage: Notification = JSON.parse(message.body)
                    this.userService.notificationState$.pipe(take(1)).subscribe(response => {
                        const newUnSeen = response?.unSeenNotification as number + 1;
                        response?.notifications.pop()
                        const newNotification: Notification[] = [newMessage, ...response?.notifications as []]

                        this.userService.notificationState$.next({ unSeenNotification: newUnSeen, notifications: newNotification })
                    })

                    this.messageService.add({
                        severity: "info",
                        detail: newMessage.message,
                        summary: "Thông Báo Mới"
                    })
                })
            },
            onStompError: () => {
                console.log("error")
            },
            onDisconnect: () => {
                console.log("disconnect");
            }
        })

        this.stompClient.activate()
    }

    onDisconnect() {
        this.stompClient.unsubscribe
        this.stompClient.deactivate()
    }
}