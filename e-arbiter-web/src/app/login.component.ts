import {Component, OnInit} from "@angular/core";
import {AuthService} from "./shared/service/auth.service";
import {RouteService} from "./shared/service/route.service";

@Component({
  selector: 'arb-login',
  template: `
    <div class="full-page-view">
      <h1>e-Arbiter</h1>
      <div *ngIf="!authService.getLoggedInUser(); else goToPanel" class="full-page-view__subcard">
        <h3>Nie jesteś zalogowany</h3>
        <button (click)="loginUser()" class="ui teal button huge">
          <i class="github icon"></i>Zaloguj się
        </button>
      </div>

      <ng-template #goToPanel class="full-page-view__subcard">
        <img *ngIf="this.authService.getLoggedInUser()"
             class="ui small centered circular image" 
             src="{{authService.getUserImgLink()}}">
        <h3>Witaj, {{authService.getLoggedInUser().name}}</h3>
        <button (click)="goToDashboard()" class="ui teal button huge">
          <i class="play icon"></i>Przejdź do aplikacji
        </button>
      </ng-template>
    </div>
  `
})
export class LoginComponent implements OnInit {

  constructor(public authService: AuthService, private routeService: RouteService) {

  }

  ngOnInit(): void {
    if (this.authService.hasAuthToken()) {
      this.authService.logIn();
    }
  }

  public loginUser() {
    this.routeService.goToLoginServerPage();
  }

  public goToDashboard() {
    this.routeService.goToDashboard();
  }
}
