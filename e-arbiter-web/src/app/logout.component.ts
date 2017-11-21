import {Component, OnInit} from '@angular/core';
import {RouteService} from './shared/service/route.service';

@Component({
  selector: 'arb-logout',
  template: `
    <div class="ui container full-page-view">
      <h1>e-Arbiter</h1>
      <h3>Zostałeś wylogowany</h3>
    </div>`
})
export class LogoutComponent implements OnInit {

  private readonly redirectTimeoutInMs = 3000;

  constructor(private routeService: RouteService) {
  }

  ngOnInit(): void {
    setTimeout(() => this.routeService.goToLoginPage(), this.redirectTimeoutInMs);
  }
}
