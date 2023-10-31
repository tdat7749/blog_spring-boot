import {Component, ViewEncapsulation} from '@angular/core';
import {combineLatest, debounceTime, distinctUntilChanged, Observable, Subject, switchMap, takeUntil, tap} from "rxjs";
import {SortBy} from "../../../core/types/api-response.type";
import {ConfirmationService, MenuItem, MessageService} from "primeng/api";
import {LoadingService} from "../../../core/services/loading.service";
import {PaginationService} from "../../../core/services/pagination.service";
import {Series, SeriesListPost} from "../../../core/types/series.type";
import {SeriesService} from "../../../core/services/series.service";

@Component({
  selector: 'app-admin-series',
  templateUrl: './series.component.html',
  styleUrls: ['./series.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SeriesComponent {
  isLoading: boolean = false
  isGetLoading: boolean = false

  listSeries: Series[] = []
  series:SeriesListPost | null = null
  totalPage: number


  search$: Observable<[number, string, SortBy]>

  destroy$ = new Subject<void>()

  items: MenuItem[] | undefined;

  activeItem: MenuItem | undefined;

  seriesDetailVisible:boolean = false


  constructor(
      private seriesService:SeriesService,
      private messageService:MessageService,
      private confirmService:ConfirmationService,
      public loadingService:LoadingService,
      private paginationService:PaginationService
  ) {
  }

  ngOnInit() {
    this.items = [
      { label: 'Tất Cả', icon: 'pi pi-fw pi-book' }
    ];
    this.activeItem = this.items[0];
    this.getAllSeries()
  }

  getAllSeries(){
    this.search$ = combineLatest([
      this.paginationService.pageIndex$,
      this.paginationService.keyword$,
      this.paginationService.sortBy$
    ]).pipe(takeUntil(this.destroy$))

    this.search$.pipe(
        takeUntil(this.destroy$),
        tap(() => this.loadingService.startLoading()),
        debounceTime(700),
        distinctUntilChanged(),
        switchMap(([pageIndex, keyword, sortBy]) => {
          return this.seriesService.getAllSeries(pageIndex,keyword, sortBy)
        })
    ).subscribe({
      next: (response) => {
        this.totalPage = response.data.totalPage
        this.listSeries = response.data.data
        this.loadingService.stopLoading()
      },
      error: (error) => {
        this.messageService.add({
          severity: "error",
          detail: error,
          summary: "Lỗi"
        })
        this.loadingService.stopLoading()
      }
    })
  }

  onChangeSearch(event: any) {
    this.paginationService.updateKeyword(event.target.value)
  }

  onChangePageIndex(event: any) {
    this.paginationService.updatePageIndex(event.page)
  }

  onDeleteSeries(id:number,event:Event){
    this.confirmService.confirm({
      target:event.target as EventTarget,
      message:"Bạn có chắc chắn muốn xóa series này ?",
      header:"Xóa Series",
      icon:"pi pi-exclamation-triangle",
      accept:() => {
        this.isLoading = true
        this.seriesService.deleteSeries(id).pipe(takeUntil(this.destroy$))
            .subscribe({
              next:(response) =>{
                this.listSeries = this.listSeries.filter((item) => item.id !== id)
                this.messageService.add({
                  severity:"success",
                  detail: response.message,
                  summary:"Thành Công"
                })
                this.isLoading = false
              },
              error:(error) => {
                this.messageService.add({
                  severity:"error",
                  detail: error,
                  summary:"Thất Bại"
                })
                this.isLoading = false
              }
            })
      },
      reject:() => {
        this.messageService.add({ severity: 'info', summary: 'Thông Báo', detail: 'Bạn đã hủy việc xóa bài viết' });
      }
    })
  }

  onPreviewDetail(slug:string){
    this.isLoading = true
    this.seriesService.getSeriesDetailBySlug(slug).pipe(takeUntil(this.destroy$))
        .subscribe({
          next:(response) => {
            this.series = response.data
            this.isLoading = false
            this.seriesDetailVisible = true
          },
          error:(error) => {
            this.messageService.add({
              severity:"error",
              detail:error,
              summary:"Lỗi"
            })
            this.isLoading = false
          }
        })
  }

  onActiveItemChange(event: MenuItem) {
    this.activeItem = event;
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
    this.paginationService.onDestroy()
  }
}
