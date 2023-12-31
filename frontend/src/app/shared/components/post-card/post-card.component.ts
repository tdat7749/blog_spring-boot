import {Component, Input} from '@angular/core';
import {PostCardInfo, PostList} from "../../../core/types/post.type";

@Component({
  selector: 'main-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.css']
})
export class PostCardComponent {
  @Input() cardInfo: PostList
}
