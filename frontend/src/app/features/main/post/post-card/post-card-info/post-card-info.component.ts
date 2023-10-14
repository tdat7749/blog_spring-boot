import {Component, Input} from '@angular/core';
import {PostCardInfo} from "../../../../../core/types/post.type";

@Component({
  selector: 'main-post-card-info',
  templateUrl: './post-card-info.component.html',
  styleUrls: ['./post-card-info.component.css']
})
export class PostCardInfoComponent {
  @Input()  cardInfo: PostCardInfo

}
