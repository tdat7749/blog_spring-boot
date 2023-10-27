import { Component, OnDestroy, OnInit } from '@angular/core';
import { SeriesService } from 'src/app/core/services/series.service';
import { MessageService } from "primeng/api";
import { LoadingService } from "../../../core/services/loading.service";
import { PaginationService } from "../../../core/services/pagination.service";
import {
  combineLatest,
  debounce,
  debounceTime,
  distinctUntilChanged,
  Observable,
  Subject,
  switchMap,
  takeUntil,
  tap
} from "rxjs";
import { SortBy } from "../../../core/types/api-response.type";
import { Series, SeriesListPost } from "../../../core/types/series.type";

@Component({
  selector: 'app-series',
  templateUrl: './series.component.html',
  styleUrls: ['./series.component.css']
})
export class SeriesComponent implements OnInit, OnDestroy {

  destroy$ = new Subject<void>()
  search$: Observable<[number, string, SortBy]>

  totalPage: number
  listSeries: Series[] = []

  constructor(
    private seriesService: SeriesService,
    private messageService: MessageService,
    public loadingService: LoadingService,
    private paginationService: PaginationService
  ) {

  }

  ngOnInit() {
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
        return this.seriesService.getAllSeries(pageIndex, keyword, sortBy)
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

  ngOnDestroy() {
    this.paginationService.onDestroy()
    this.destroy$.next()
    this.destroy$.complete()
  }
}
