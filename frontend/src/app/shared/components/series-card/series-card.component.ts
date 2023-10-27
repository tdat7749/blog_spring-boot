import {Component, Input} from '@angular/core';
import {Series} from "../../../core/types/series.type";

@Component({
  selector: 'main-series-card',
  templateUrl: './series-card.component.html',
  styleUrls: ['./series-card.component.css']
})
export class SeriesCardComponent {
  @Input("series") series:Series
}
