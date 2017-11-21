import {Component, OnInit} from '@angular/core';
import {RouteService} from './shared/service/route.service';

@Component({
  selector: 'arb-not-found',
  template: `
    <div class="ui container full-page-view">
      <h1>e-Arbiter</h1>
      <h3>Oops! Chyba zabłądziłeś!</h3>
      <h4>Jeżeli jednak powinna być tu inna strona, skontaktuj się z adminem.</h4>
    </div>
  `
})
export class NotFoundComponent implements OnInit {

  private readonly redirectTimeoutInMs = 5000;

  constructor(private routeService: RouteService) {
  }

  ngOnInit(): void {
    setTimeout(() => this.routeService.goToDashboard(), this.redirectTimeoutInMs);
  }
}
