import {Component, OnInit} from '@angular/core';
import {listNav} from "../../../../../shared/commons/shared";
import {AuthService} from "../../../../../core/services/auth.service";
import {User} from "../../../../../core/types/user.type";

@Component({
  selector: 'main-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit{

  userInfo: User | null = null
  listNav = listNav
  isOpen:boolean = false;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
    this.authService.userState$.subscribe(response => {
      this.userInfo = response
    })
  }
}
