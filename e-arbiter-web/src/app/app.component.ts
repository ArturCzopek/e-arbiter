import {Component} from '@angular/core';

@Component({
  selector: 'arb-root',
  template: `
    <div class="ui inverted vertical masthead center aligned segment main-page">
      <router-outlet></router-outlet>
    </div>
  `,
  styleUrls: ['./app.scss']
})
export class AppComponent {
}
