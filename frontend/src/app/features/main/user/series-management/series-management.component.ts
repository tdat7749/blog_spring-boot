import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {SeriesService} from "../../../../core/services/series.service";
import {MessageService} from "primeng/api";
import {Series} from "../../../../core/types/series.type";

@Component({
  selector: 'main-series-management',
  templateUrl: './series-management.component.html',
  styleUrls: ['./series-management.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SeriesManagementComponent implements OnInit{

  isLoading:boolean = false



  listSeries:Series[] = []

  constructor(private seriesService:SeriesService,private messageService:MessageService) {

  }

  ngOnInit() {
    this.isLoading = true
    this.seriesService.getSeriesByCurrentUser().subscribe({
      next:(response) =>{
        this.isLoading = false
        this.listSeries = response.data
      },
      error:(error) =>{
        this.isLoading = false
        this.messageService.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
      }
    })
  }

  onChangeSearch(event:any){

  }

  onChangePageIndex(event:any){

  }

  onDeleteSeries(id:number){
    this.seriesService.deleteSeries(id).subscribe({
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
  }
}
