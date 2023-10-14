import {Component, OnInit} from '@angular/core';
import {AuthService} from "./core/services/auth.service";
import {CookieService} from "ngx-cookie-service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  constructor(private authService: AuthService,private cookieService:CookieService) {

  }
  title = 'bố thăng docker rách';

  ngOnInit() {
    if(this.cookieService.get("accessToken") !== null &&  this.cookieService.get("accessToken") !== "" && this.authService.getCurrentUser() === null){
      this.authService.getMe().subscribe(
          (response) => {
            this.authService.userState$.next(response.data)
            this.authService.setCurrentUser(response.data)
          }
      )
    }
  }
}
