import {Component, OnInit} from '@angular/core';
import {listNav} from "../../../../../shared/commons/shared";
import {AuthService} from "../../../../../core/services/auth.service";
import {User} from "../../../../../core/types/user.type";

@Component({
  selector: 'main-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent{


  listNav = listNav
  isOpen:boolean = false;

  constructor(private authService: AuthService) {
  }

}