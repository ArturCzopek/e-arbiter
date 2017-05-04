import {Component} from '@angular/core';

@Component({
  selector: 'arb-root',
  template: `
    <arb-header></arb-header>
    <div class="main-tab" style="margin-top: 70px;">
      <router-outlet></router-outlet>
    </div>
  `,
  styleUrls: ['./app.scss']
})
export class AppComponent {
}
