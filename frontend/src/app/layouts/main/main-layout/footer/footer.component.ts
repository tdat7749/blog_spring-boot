import {Component, ViewEncapsulation} from '@angular/core';
import {listNav} from "../../../../shared/commons/shared";

@Component({
  selector: 'main-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class FooterComponent {
  listNav = listNav
}
