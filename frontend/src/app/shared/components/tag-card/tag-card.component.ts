import {Component, Input} from '@angular/core';
import {Tag} from "../../../core/types/tag.type";

@Component({
  selector: 'main-tag-card',
  templateUrl: './tag-card.component.html',
  styleUrls: ['./tag-card.component.css']
})
export class TagCardComponent {
  @Input("tag") tag:Tag
}
