import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {User} from "../../../../core/types/user.type";
import {FollowService} from "../../../../core/services/follow.service";
import {MessageService} from "primeng/api";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'main-author-information',
  templateUrl: './author-information.component.html',
  styleUrls: ['./author-information.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AuthorInformationComponent implements OnInit{
  @Input("author") author:User
  @Input("userName") userName:string
  isFollow:boolean = false
  currentUser:User | null

  isLoading:boolean = false

  protected readonly Number = Number;
  constructor(private _router:ActivatedRoute,private followService:FollowService,private messageService:MessageService) {

  }

  ngOnInit() {
    this.followService.checkFollowed(this.userName).subscribe({
      next:(response) =>{
        this.isFollow = response.data
      },
      error:(error) =>{
        this.messageService.add({
          severity:"error",
          detail:error,
          summary:"Lỗi"
        })
      }
    })
  }

  onFollow(id:number){
    this.isLoading = true
    this.followService.follow(id).subscribe({
      next:(response) =>{
        this.messageService.add({
          severity:"success",
          detail:response.message,
          summary:"Lỗi"
        })
        this.isFollow = true
        this.isLoading = false
      },
      error:(error) =>{
        console.log(error)
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
     this.isLoading = true
    this.followService.unFollow(id).subscribe({
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
}
