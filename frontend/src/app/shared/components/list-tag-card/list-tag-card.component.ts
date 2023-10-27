import {Component, Input} from '@angular/core';
import {Tag} from "../../../core/types/tag.type";

@Component({
  selector: 'main-list-tag-card',
  templateUrl: './list-tag-card.component.html',
  styleUrls: ['./list-tag-card.component.css']
})
export class ListTagCardComponent {
  @Input("listTag") listTag:Tag[] = []
}
