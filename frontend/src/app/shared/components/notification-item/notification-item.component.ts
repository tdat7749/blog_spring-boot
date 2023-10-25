import {Component, Input, ViewEncapsulation} from '@angular/core';
import {Notification} from "../../../core/types/noti.type";
import {UserService} from "../../../core/services/user.service";

@Component({
  selector: 'main-notification-item',
  templateUrl: './notification-item.component.html',
  styleUrls: ['./notification-item.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class NotificationItemComponent {
  @Input("item") item:Notification

  constructor(private userService:UserService) {
  }

  readNotification(notification:Notification){
    this.userService.readNotification(notification.id).subscribe({
      next:(response) => {
        notification.read = true
      }
    })
  }
}
