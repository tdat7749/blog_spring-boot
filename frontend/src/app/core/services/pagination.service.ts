import { Injectable } from "@angular/core";
import {BehaviorSubject, Subject, takeUntil} from "rxjs";
import { SortBy } from "../types/api-response.type";

@Injectable({
    providedIn: "root"
})
export class PaginationService {
    pageIndex$ = new BehaviorSubject<number>(0)
    keyword$ = new BehaviorSubject<string>("")
    sortBy$ = new BehaviorSubject<SortBy>("createdAt")

    destroy$ = new Subject<void>();

    updatePageIndex(newIndex: number) {
        this.pageIndex$.next(newIndex)
    }

    updateKeyword(newKeyword: string) {
        this.keyword$.next(newKeyword)
    }

    updateSortBy(newSortBy: SortBy) {
        this.sortBy$.next(newSortBy)
    }

    onDestroy() {
        this.pageIndex$.next(0)
        this.keyword$.next("")
        this.sortBy$.next("createdAt")
    }
}