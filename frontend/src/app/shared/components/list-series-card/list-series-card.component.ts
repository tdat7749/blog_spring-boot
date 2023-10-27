import {Component, Input} from '@angular/core';
import {Series} from "../../../core/types/series.type";

@Component({
  selector: 'main-list-series-card',
  templateUrl: './list-series-card.component.html',
  styleUrls: ['./list-series-card.component.css']
})
export class ListSeriesCardComponent {
  @Input("listSeries") listSeries:Series[] = []
}
