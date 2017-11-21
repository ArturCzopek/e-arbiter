import {Component} from '@angular/core';

@Component({
  selector: 'arb-dashboard',
  template: `
    <div>
      <arb-header></arb-header>
      <router-outlet></router-outlet>
    </div>
  `
})
export class DashboardComponent {

}
