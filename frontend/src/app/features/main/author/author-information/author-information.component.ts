import {Component, Input, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {User} from "../../../../core/types/user.type";
import {FollowService} from "../../../../core/services/follow.service";
import {MessageService} from "primeng/api";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../../core/services/user.service";
import {AuthService} from "../../../../core/services/auth.service";
import {Subject, takeUntil} from "rxjs";

@Component({
  selector: 'main-author-information',
  templateUrl: './author-information.component.html',
  styleUrls: ['./author-information.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AuthorInformationComponent implements OnInit,OnDestroy{
  @Input("author") author:User
  @Input("userName") userName:string
  isFollow:boolean = false
  isLoading:boolean = false

  protected readonly Number = Number;

  destroy$ = new Subject<void>()
  constructor(
      private _router:ActivatedRoute,
      private followService:FollowService,
      private messageService:MessageService,
      private authService:AuthService
  ) {

  }

  ngOnInit() {
    this.followService.checkFollowed(this.userName)
        .pipe(
            takeUntil(this.destroy$)
        ).subscribe({
      next:(response) =>{
        this.isFollow = response.data
      },
      error:(error) =>{
        this.isFollow = false
      }
    })
  }

  onFollow(id:number){
    if(this.authService.getCurrentUser() === null){
      this.messageService.add({
        severity:"error",
        detail:"Vui lòng đăng nhập",
        summary:"Lỗi"
      })
      return;
    }
    this.isLoading = true
    this.followService.follow(id).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.messageService.add({
          severity:"success",
          detail:response.message,
          summary:"Thành Công"
        })
        this.isFollow = true
        this.isLoading = false
      },
      error:(error) =>{
        this.messageService.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
        this.isLoading = false
      }
    })
  }

  onUnFollow(id:number){
    if(this.authService.getCurrentUser() === null){
      this.messageService.add({
        severity:"error",
        detail:"Vui lòng đăng nhập",
        summary:"Lỗi"
      })
      return;
    }
     this.isLoading = true
    this.followService.unFollow(id).pipe(takeUntil(this.destroy$)).subscribe({
      next:(response) =>{
        this.messageService.add({
          severity:"success",
          detail:response.message,
          summary:"Thành Công"
        })
        this.isFollow = false
        this.isLoading = false

      },
      error:(error) =>{
        this.messageService.add({
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
