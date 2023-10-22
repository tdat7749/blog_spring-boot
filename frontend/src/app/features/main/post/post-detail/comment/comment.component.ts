import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import {noWhiteSpaceValidator} from "../../../../../shared/validators/no-white-space.validator";
import {CommentService} from "../../../../../core/services/comment.service";
import {PaginationService} from "../../../../../core/services/pagination.service";
import {combineLatest, map, Observable, retry, switchMap, tap} from "rxjs";
import {SortBy} from "../../../../../core/types/api-response.type";
import {MessageService} from "primeng/api";
import {Comment, CreateComment, EditComment} from "../../../../../core/types/comment.type";
import {User} from "../../../../../core/types/user.type";
import {AuthService} from "../../../../../core/services/auth.service";

@Component({
  selector: 'main-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CommentComponent implements OnInit{
  @Input("postId") postId:number


  listComment:Comment[]
  slug:string
  createCommentForm:FormGroup

  currentUser:User | null

  isLoading:boolean = false
  search$:Observable<[number,SortBy]>
  totalPage:number

  constructor(
      private paginationService:PaginationService,
      private _router:ActivatedRoute,
      private fb:FormBuilder,
      private commentService:CommentService,
      private messageService:MessageService,
      private authService:AuthService
  ) {

  }

  ngOnInit() {
    this.createCommentForm = this.fb.group({
      content:[
          '',
          Validators.compose([
              Validators.required,
              Validators.maxLength(300),
              noWhiteSpaceValidator()
          ])
      ]
    })

    this.currentUser = this.authService.getCurrentUser()

    this._router.paramMap.subscribe((params) =>{
      this.slug = params.get("slug") as string
    })

    this.search$ = combineLatest([
        this.paginationService.pageIndex$,
        this.paginationService.sortBy$
    ])
    this.search$.pipe(
        retry(2),
        switchMap(([pageIndex,sortBy]) =>{
          return this.commentService.getCommentPost(this.slug,pageIndex,sortBy)
        })
    ).subscribe({
      next:(response) =>{
        this.listComment = response.data.data.map((item:Comment) =>{
            return {
                ...item,
                showReplyForm: false,
                showEditForm:false,
                childComment: item.childComment.map((child:Comment) => {
                    return {
                        ...child,
                        showEditForm:false
                    }
                })
            }
        })
        this.totalPage =response.data.totalPage
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

    onChangePageIndex(event:any){
        this.paginationService.updatePageIndex(event.page)
    }


  onCreateComment(){
      if(this.currentUser === null){
          this.messageService.add({
              severity:"error",
              detail:"Vui lòng đăng nhập để bình luận",
              summary:"Lỗi"
          })
      }

      if(this.createCommentForm.valid){
          const formValue = this.createCommentForm.value
          const data:CreateComment = {
              content: formValue.content
          }
          this.isLoading = true
          this.commentService.createComment(data,this.postId).subscribe({
              next:(response) =>{
                  this.messageService.add({
                      severity:"success",
                      detail:response.message,
                      summary:"Thành Công"
                  })
                  this.isLoading = false
                  this.listComment = [response.data,...this.listComment]
                  this.createCommentForm.reset()
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

  onCreateChildComment(parentId:number,form:NgForm){
        if(this.currentUser === null){
            this.messageService.add({
                severity:"error",
                detail:"Vui lòng đăng nhập để bình luận",
                summary:"Lỗi"
            })
        }


        if(form.valid){
            const formValue = form.value
            const data:CreateComment = {
                content: formValue.content,
                parentId:parentId
            }
            this.isLoading = true
            this.commentService.createComment(data,this.postId).subscribe({
                next:(response) =>{
                    this.messageService.add({
                        severity:"success",
                        detail:response.message,
                        summary:"Thành Công"
                    })
                    this.isLoading = false
                    this.listComment = this.listComment.map((item:Comment) => {
                        if(item.id === parentId){
                            item.childComment = [...item.childComment,{...response.data,showReplyForm:false,showEditForm:false}]
                        }
                        return item;
                    })
                    form.reset()
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

  onShowReply(comment:Comment){
      comment.showReplyForm = !comment.showReplyForm
  }

  onShowEdit(comment:Comment){
      comment.showEditForm = !comment.showEditForm
  }

  onDeleteComment(id:number,parentId?:number){
      this.isLoading = true
      this.commentService.deleteComment(id).subscribe({
          next:(response) =>{
              this.messageService.add({
                  severity:"success",
                  detail:response.message,
                  summary:"Thành Công"
              })
              if(!parentId){
                  this.listComment = this.listComment.filter((item:Comment) => item.id !== id)
              }else{
                  this.listComment = this.listComment.map((item:Comment) =>{
                      if(item.id === parentId){
                          item.childComment = item.childComment.filter((child:Comment) => child.id !== id)
                      }
                      return item;
                  })
              }
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

  onEditComment(comment:Comment,form:NgForm,parentId?:number){
      if(this.currentUser === null){
          this.messageService.add({
              severity:"error",
              detail:"Vui lòng đăng nhập để bình luận",
              summary:"Lỗi"
          })
      }

      if(form.valid){
          const formValue = form.value
          const data:EditComment = {
              id:comment.id,
              content: formValue.content
          }
          this.isLoading = true
          this.commentService.editComment(data).subscribe({
              next:(response) =>{
                  this.messageService.add({
                      severity:"success",
                      detail:response.message,
                      summary:"Thành Công"
                  })
                  this.isLoading = false
                  comment.content = response.data.content
                  comment.showEditForm = false
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
}
