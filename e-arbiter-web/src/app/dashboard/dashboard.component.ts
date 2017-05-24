import {Component} from "@angular/core";

@Component({
  selector: 'arb-dashboard',
  template: `
    <div class="ui inverted vertical masthead center aligned segment dashboard">
      <arb-header></arb-header>
      <div class="main-tab" style="margin-top: 70px;">
        <router-outlet></router-outlet>
      </div>
    </div>
  `,
  styleUrls: ['./dashboard.scss']
})
export class DashboardComponent {

}
