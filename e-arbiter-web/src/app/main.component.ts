import {Component, OnInit} from "@angular/core";
import {AuthService} from "./shared/service/auth.service";
import {environment} from "environments/environment";
import {Router} from "@angular/router";

declare var window: any;

@Component({
  selector: 'arb-main',
  template: `

    <div class="ui text container">
      <h1 class="ui inverted header">e-Arbiter</h1>
      <div *ngIf="!authService.getLoggedInUser(); else goToPanel">
        <h2>Nie jesteś zalogowany</h2>
        <div class="ui vertical labeled icon buttons">
          <button (click)="loginUser()" class="ui black big inverted button">
            <i class="github large icon"></i>Zaloguj się
          </button>
        </div>

      </div>
      <ng-template #goToPanel>
        <img class="ui small centered circular image" *ngIf="this.authService.getLoggedInUser()"
             src="{{authService.getUserImgLink()}}">
        <h2>Witaj, {{authService.getLoggedInUser().name}}</h2>
        <button (click)="goToDashboard()" class="ui black inverted big button">Przejdź do aplikacji</button>
      </ng-template>
    </div>
  `,
  styleUrls: ['./app.scss']
})
export class MainComponent implements OnInit {

  constructor(public userService: AuthService, public router: Router) {

  }

  ngOnInit(): void {
    if (localStorage.getItem(environment.authToken) || this.userService.getTokenFromCookie()) {
      this.userService.logIn();
    }
  }

  public loginUser() {
    console.log(environment.server.auth.loginUrl)
    window.location = `${environment.server.auth.loginUrl}`;
  }

  public goToDashboard() {
    this.router.navigate([environment.client.dashboard.url]);
  }
}
