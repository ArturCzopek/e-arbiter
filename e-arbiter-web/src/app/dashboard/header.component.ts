import {AfterViewInit, Component} from "@angular/core";
import {environment} from "../../environments/environment";
import {AuthService} from "../shared/service/auth.service";

declare var $: any;

@Component({
  selector: 'arb-header',
  template: `
    <div class="ui teal inverted fixed menu">
      <div class="ui container">
        <a href="#{{dashboardUrl}}" class="item header__title">e-Arbiter</a>
        <a [routerLink]="[mainPanelUrl]" routerLinkActive="active" class="item header__link">
          Dashboard
        </a>
        <a [routerLink]="[activeTournamentsPanelUrl]" routerLinkActive="active" class="item header__link">
          Aktywne turnieje
        </a>
        <a [routerLink]="[tournamentManagementPanelUrl]" routerLinkActive="active" class="item header__link">
          Zarządzaj turniejami
        </a>
        <a [routerLink]="[developmentPanelUrl]" routerLinkActive="active" class="item header__link">
          Development card
        </a>
        <div class="ui simple dropdown item right ">
          <img *ngIf="authService.isLoggedInUser()"
               class="ui avatar image"
               src="{{authService.getUserImgLink()}}">
          <p class="header__link header__link--dropdown">{{authService.getLoggedInUserName()}}</p>
          <i class="dropdown icon"></i>
          <div class="menu">
            <a *ngIf="authService.isLoggedInUserAdmin()" [routerLink]="[adminPanelUrl]"
               class="header__link header__link--dropdown-item">
              Panel administratora
            </a>
            <a class="header__link header__link--dropdown-item" (click)="authService.logOut()">
              Wyloguj się
            </a>
          </div>
        </div>
      </div>
    </div>
  `
})
export class HeaderComponent implements AfterViewInit {

  public readonly dashboardUrl = environment.client.dashboard.url;
  public readonly mainPanelUrl = environment.client.dashboard.mainPanelUrl;
  public readonly tournamentManagementPanelUrl = environment.client.dashboard.managementPanelUrl;
  public readonly activeTournamentsPanelUrl = environment.client.dashboard.activeTournamentsPanelUrl;
  public readonly adminPanelUrl = environment.client.dashboard.adminPanelUrl;

  public readonly developmentPanelUrl = environment.client.dashboard.developmentPanelUrl;

  constructor(public authService: AuthService) {
  }

  ngAfterViewInit(): void {
    $('.ui.dropdown')
      .dropdown();
  }
}
