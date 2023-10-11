import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {AuthService} from "../../../../core/services/auth.service";
import {User} from "../../../../core/types/user.type";
import {listNav} from "../../../../shared/commons/shared";
import {SideBarComponent} from "./side-bar/side-bar.component";
import {ActivatedRoute, Routes} from "@angular/router";

@Component({
  selector: 'main-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy{
  userInfo: User | null = null

  listNav=listNav

  route:string | null = null

  @ViewChild("sideBar") sideBar: SideBarComponent | undefined

  constructor(private authService: AuthService,private router:ActivatedRoute  ) {

  }

  ngOnInit() {
    this.authService.userState$.subscribe(response => {
      this.userInfo = response
    })


    console.log(this.router)
  }

  logout() {
    this.authService.logout()
  }

  openSideBar(){
    if(this.sideBar){
      this.sideBar.isOpen = true
    }
  }

  ngOnDestroy() {
    this.authService.userState$.unsubscribe()
  }

}
