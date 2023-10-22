import {Component, Input} from '@angular/core';
import {PostCardInfo, PostList} from "../../../core/types/post.type";

@Component({
  selector: 'main-list-post-card',
  templateUrl: './list-post-card.component.html',
  styleUrls: ['./list-post-card.component.css']
})
export class ListPostCardComponent {
  @Input("side") side:string
  @Input("listPost") listPost:PostList[]
}
