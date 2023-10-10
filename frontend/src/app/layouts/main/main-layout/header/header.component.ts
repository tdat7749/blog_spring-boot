import { Component } from '@angular/core';
import {AuthService} from "../../../../core/services/auth.service";
import {User} from "../../../../core/types/user.type";

@Component({
  selector: 'main-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  userInfo: User | undefined
  constructor(private authService: AuthService) {
  }
}
