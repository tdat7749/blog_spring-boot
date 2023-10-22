import {AfterViewInit, Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {userSideBar} from "../../../../shared/commons/shared";
import {ActivatedRoute} from "@angular/router";
import {BehaviorSubject, Subject} from "rxjs";

@Component({
  selector: 'main-user-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SidebarComponent{
  userSideBar = userSideBar



  constructor(private _route:ActivatedRoute) {
  }

}
