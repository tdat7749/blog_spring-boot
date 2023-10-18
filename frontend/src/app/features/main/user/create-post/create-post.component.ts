import {AfterViewInit, Component, OnInit, QueryList, ViewChild, ViewChildren, ViewEncapsulation} from '@angular/core';
import {PostService} from "../../../../core/services/post.service";
import {FileStorageService} from "../../../../core/services/file-storage.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MessageService} from "primeng/api";
import {noWhiteSpaceValidator} from "../../../../shared/validators/no-white-space.validator";
import {Editor} from "primeng/editor";
import {FileUpload} from "primeng/fileupload";
import {fileUploadValidator} from "../../../../shared/validators/file-upload.validator";
import Delta from 'quill-delta';

@Component({
  selector: 'main-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CreatePostComponent implements OnInit,AfterViewInit{

  createPostForm : FormGroup
  previewImage:string = ""
  isLoading:boolean = false

  @ViewChildren(Editor) editors: QueryList<Editor>;
  @ViewChild("fileUpload") fileUpload: FileUpload

  constructor(
      private postService:PostService,
      private fileStorageService:FileStorageService,
      private fb:FormBuilder,
      private messageService:MessageService
  ) {}

  ngOnInit() {
    this.createPostForm = this.fb.group({
      title:[
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(100),
          noWhiteSpaceValidator()
        ])
      ],
      content:[
        '',
        Validators.compose([
          Validators.required,
        ])
      ],
      summary:[
        '',
        Validators.compose([
          Validators.required,
        ])
      ],
      tags:[
        null,
        Validators.required
      ],
      thumbnail:[
        null,
        Validators.required
      ]
    })
  }

  ngAfterViewInit() {
    this.editors.map(editor => {
      const quill = editor.getQuill();
      const toolbar = quill.getModule('toolbar');

      toolbar.addHandler('image', () => {
        let fileInput = toolbar.container.querySelector('button.ql-image');
        fileInput = document.createElement("input")
        fileInput.setAttribute("type",'file')
        fileInput.click()
        fileInput.addEventListener('change', () => {
          const file = fileInput.files[0]
          const result = fileUploadValidator(file)
          if(!result.success){
            this.messageService.add({
              severity: "error",
              detail: result.message,
              summary:"Lỗi"
            })
            return;
          }

          let formData = new FormData()
          formData.set("file",file)
          this.fileStorageService.upload(formData).subscribe({
            next:(response) =>{
              const range = quill.getSelection(true);
              quill.updateContents(
                  new Delta()
                      .retain(range.index)
                      .delete(range.length)
                      .insert({ image: response.data }),
                  'user'
              );
              quill.setSelection(range.index + 1, 'silent');
              fileInput.value = '';
            },
            error:(error) =>{
              this.messageService.add({
                severity:"error",
                detail:error,
                summary:"Lỗi"
              })
            }
          })
      })
    })
  })
}

  onCreatePost(){

  }

  onSelectImage(event: any){
    const file = event.files[0]
    if(file){
      const result = fileUploadValidator(file)
      if(!result.success){
        this.messageService.add({
          severity: "error",
          detail: result.message,
          summary:"Lỗi"
        })
        return;
      }
      this.previewImage = file?.objectURL.changingThisBreaksApplicationSecurity
    }
  }

  onClearUpload(){
    this.previewImage = ""
    this.fileUpload.clear()
  }

}
