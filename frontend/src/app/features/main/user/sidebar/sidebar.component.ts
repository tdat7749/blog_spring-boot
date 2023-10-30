import {AfterViewInit, Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {userSideBar} from "../../../../shared/commons/shared";
import {ActivatedRoute} from "@angular/router";
import {BehaviorSubject, Subject, takeUntil} from "rxjs";
import {SelectPathService} from "../../../../core/services/select-path.service";

@Component({
  selector: 'main-user-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SidebarComponent implements OnInit,OnDestroy{
  userSideBar = userSideBar

  currentPath:string | null = null

  destroy$ = new Subject<void>()

  constructor(private selectPathService:SelectPathService) {

  }

  ngOnInit() {
    this.selectPathService.path$.pipe(takeUntil(this.destroy$)).subscribe(path => {
      this.currentPath = path
    })
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
