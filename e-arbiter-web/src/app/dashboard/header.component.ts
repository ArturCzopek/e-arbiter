import {AfterViewInit, Component} from "@angular/core";
import {environment} from "../../environments/environment";
import {UserService} from "../shared/service/user.service";

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
            <img class="ui avatar image" *ngIf="this.userService.getLoggedInUser()"
                 src="{{userService.getUserImgLink()}}">
            <span>{{userService.getLoggedInUser().name}}</span>
            <i class="dropdown icon"></i>
            <div class="menu">
              <a class="item">Moje dane</a>
              <a class="item">Cokolwiek</a>
              <div class="divider"></div>
              <a class="item" (click)="userService.logOut()">Wyloguj się</a>
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

  constructor(public userService: UserService) {
  }

  ngAfterViewInit(): void {
    $('.ui.dropdown')
      .dropdown();
  }
}
