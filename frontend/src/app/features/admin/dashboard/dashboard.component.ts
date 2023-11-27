import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {Statistical} from "../../../core/types/statistical.type";
import {StatisticalService} from "../../../core/services/statistical.service";
import {LoadingService} from "../../../core/services/loading.service";
import {Subject, takeUntil} from "rxjs";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DashboardComponent implements OnInit,OnDestroy{
  statistical:Statistical
  isGetLoading:boolean = false

  destroy$ = new Subject<void>

  constructor(
      private statisticalService:StatisticalService,
      public loadingService:LoadingService,
      private messageService:MessageService
  ) {

  }

  ngOnInit() {
    this.loadingService.startLoading()
    this.statisticalService.getDashboard().pipe(takeUntil(this.destroy$))
        .subscribe({
          next:(response) => {
            this.statistical = response.data
            this.loadingService.stopLoading()
          },
          error:(error) => {
            this.messageService.add({
              severity:"error",
              detail: error,
              summary:"Lỗi"
            })
            this.loadingService.stopLoading()
          }
        })
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
