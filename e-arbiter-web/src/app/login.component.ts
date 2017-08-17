import {Component, OnInit} from "@angular/core";
import {AuthService} from "./shared/service/auth.service";
import {RouteService} from "./shared/service/route.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'arb-login',
  template: `
    <div class="ui container full-page-view">
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

  constructor(public authService: AuthService, private routeService: RouteService, private route: ActivatedRoute) {

  }

  public ngOnInit(): void {

    const tokenFromRouteParams = this.authService.getTokenFromRouteParams(this.route);
    const tokenFromLocalStorage = this.authService.getTokenFromLocalStorage();

    if (tokenFromRouteParams && tokenFromRouteParams.length > 0) {
      this.logInWithTokenFromRouteParam(tokenFromRouteParams);
    } else if (tokenFromLocalStorage && tokenFromLocalStorage.length > 0) {
      this.authService.logIn();
    }
  }

  public loginUser() {
    this.routeService.goToLoginServerPage();
  }

  public goToDashboard() {
    this.routeService.goToDashboard();
  }

  private logInWithTokenFromRouteParam(tokenFromRouteParams: string) {
    if (tokenFromRouteParams.length > 0) {
      this.authService.setToken(tokenFromRouteParams);
      this.routeService.goToLoginPage();
    }
  }
}
