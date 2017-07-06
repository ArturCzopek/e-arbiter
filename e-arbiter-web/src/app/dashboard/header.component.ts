import {AfterViewInit, Component} from "@angular/core";
import {environment} from "../../environments/environment";
import {AuthService} from "../shared/service/auth.service";

declare var $: any;

@Component({
  selector: 'arb-header',
  template: `
    <div class="ui container header">
      <div class="ui large secondary inverted pointing menu">
        <a [routerLink]="[dashboardUrl]" routerLinkActive="active" class="item">Strona główna</a>
        <a class="item">Otwarte turnieje</a>
        <a class="item">Moje turnieje</a>
        <a class="item">Ostatnie wyniki</a>
        <div class="right menu">
          <div class="ui dropdown item">
            <img class="ui avatar image" *ngIf="this.authService.getLoggedInUser()"
                 src="{{authService.getUserImgLink()}}">
            <span>{{authService.getLoggedInUser().name}}</span>
            <i class="dropdown icon"></i>
            <div class="menu">
              <a class="item">Moje dane</a>
              <a class="item">Cokolwiek</a>
              <div class="divider"></div>
              <a class="item" (click)="authService.logOut()">Wyloguj się</a>
            </div>
          </div>
        </div>

      </div>
    </div>
  `,
  styleUrls: ['./dashboard.scss']
})
export class HeaderComponent implements AfterViewInit {
  public dashboardUrl = environment.client.dashboard.url;

  constructor(public authService: AuthService) {
  }

  ngAfterViewInit(): void {
    $('.ui.dropdown')
      .dropdown();
  }
}
