import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {SeriesService} from "../../../../core/services/series.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {Series} from "../../../../core/types/series.type";
import {LoadingService} from "../../../../core/services/loading.service";
import {Subject, takeUntil} from "rxjs";

@Component({
  selector: 'main-series-management',
  templateUrl: './series-management.component.html',
  styleUrls: ['./series-management.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SeriesManagementComponent implements OnInit,OnDestroy{

  isLoading:boolean = false

  destroy$  = new Subject<void>()

  listSeries:Series[] = []

  constructor(
      private seriesService:SeriesService,
      private messageService:MessageService,
      public loadingService:LoadingService,
      public confirmService:ConfirmationService
  ) {

  }

  ngOnInit() {
    this.loadingService.startLoading()
    this.seriesService.getSeriesByCurrentUser().pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.listSeries = response.data
        this.loadingService.stopLoading()
      },
      error:(error) =>{
        this.messageService.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })

        this.loadingService.stopLoading()
      }
    })
  }
  onChangePageIndex(event:any){

  }

  onDeleteSeries(id:number,event:Event){
    this.confirmService.confirm({
      target:event.target as EventTarget,
      message:"Bạn có chắc chắn muốn xóa series này ?",
      header:"Xóa Series",
      icon:"pi pi-exclamation-triangle",
      accept:() => {
        this.isLoading = true
        this.seriesService.deleteSeries(id).pipe(takeUntil(this.destroy$)).subscribe({
          next:(response) =>{
            this.messageService.add({
              severity:"success",
              detail:response.message,
              summary:"Thành Công"
            })
            this.listSeries = this.listSeries.filter((item:Series) => item.id !== id)
          },
          error:(error) => {
            this.messageService.add({
              severity:"error",
              detail:error,
              summary:"Lỗi"
            })
          }
        })
      },
      reject:() => {
        this.messageService.add({ severity: 'info', summary: 'Thông Báo', detail: 'Bạn đã hủy việc xóa series' });
      }
    })
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
