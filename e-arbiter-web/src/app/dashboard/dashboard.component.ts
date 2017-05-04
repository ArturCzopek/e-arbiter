import {Component} from "@angular/core";

@Component({
  selector: 'arb-dashboard',
  template: `
      <router-outlet name="db"></router-outlet>
  `,
  styleUrls: ['./dashboard.scss']
})
export class DashboardComponent {

}
