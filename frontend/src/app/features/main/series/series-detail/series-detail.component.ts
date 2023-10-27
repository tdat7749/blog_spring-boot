import { Component, OnDestroy, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { SeriesService } from "../../../../core/services/series.service";
import { SeriesListPost } from "src/app/core/types/series.type";
import { LoadingService } from "../../../../core/services/loading.service";
import { MessageService } from "primeng/api";
import { Subject, takeUntil } from "rxjs";

@Component({
    selector: "main-series-detail",
    templateUrl: "./series-detail.component.html",
    styleUrls: ["./series-detail.component.css"]
})

export class SeriesDetailComponent implements OnInit, OnDestroy {
    slug: string

    series: SeriesListPost

    destroy$ = new Subject<void>()

    constructor(
        private _router: ActivatedRoute,
        private router: Router,
        private seriesService: SeriesService,
        public loadingService: LoadingService,
        private messageService: MessageService
    ) {

    }
    ngOnInit() {
        this._router.paramMap.pipe(takeUntil(this.destroy$)).subscribe(params => {
            this.slug = params.get("slug") as string
        })

        if (!this.slug) {
            this.router.navigate(["/"])
        }
        this.loadingService.startLoading()
        this.seriesService.getSeriesDetailBySlug(this.slug).pipe(takeUntil(this.destroy$)).subscribe({
            next: (response) => {
                console.log(response.data)
                this.series = response.data
                this.loadingService.stopLoading()
            },
            error: (error) => {
                this.messageService.add({
                    severity: "error",
                    detail: error,
                    summary: "Lỗi"
                })
                this.router.navigate(['/'])
                this.loadingService.stopLoading()
            }
        })
    }

    ngOnDestroy() {
        this.destroy$.next()
        this.destroy$.complete()
    }
}