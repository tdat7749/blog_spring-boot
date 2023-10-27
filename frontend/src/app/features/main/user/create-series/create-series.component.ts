import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SeriesService} from "../../../../core/services/series.service";
import {noWhiteSpaceValidator} from "../../../../shared/validators/no-white-space.validator";
import {CreateSeries} from "../../../../core/types/series.type";
import slugify from "slugify";
import {MessageService} from "primeng/api";
import {Router} from "@angular/router";
import {Subject, takeUntil} from "rxjs";
import {capitalizeFirstLetter} from "../../../../shared/commons/shared";

@Component({
  selector: 'main-create-series',
  templateUrl: './create-series.component.html',
  styleUrls: ['./create-series.component.css'],
  encapsulation:ViewEncapsulation.None
})
export class CreateSeriesComponent implements OnInit,OnDestroy{

  isLoading:boolean = false

  createSeriesForm:FormGroup

  destroy$ = new Subject<void>()

  constructor(private router:Router,private fb:FormBuilder,private seriesService:SeriesService,private message:MessageService) {
  }

  ngOnInit() {
    this.createSeriesForm = this.fb.group({
      title:[
          '',
          Validators.compose([
              Validators.required,
              Validators.maxLength(100),
              noWhiteSpaceValidator()
          ])
      ],
      content:[
          '',
          Validators.compose([
              Validators.required,
              noWhiteSpaceValidator()
          ])
      ]
    })
  }

  onCreateSeries(){
    const formValue = this.createSeriesForm.value

    const data:CreateSeries = {
      content: formValue.content,
      title: capitalizeFirstLetter(formValue.title),
      slug: slugify(formValue.title.toLowerCase())
    }
    this.isLoading = true
    this.seriesService.createSeries(data).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.message.add({
          severity:"success",
          detail:response.message,
          summary:"Thành Công"
        })
        this.isLoading = false
        this.router.navigate(["/nguoi-dung/quan-ly-series"])
      },
      error:(error) =>{
        this.message.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.isLoading = false
      }
    })
  }

  ngOnDestroy() {
      this.destroy$.next()
      this.destroy$.complete()
  }
}
