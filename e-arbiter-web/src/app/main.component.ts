import {Component, OnInit} from '@angular/core';
import {UserService} from "./shared/service/user.service";
import {environment} from "environments/environment";
import {ActivatedRouteSnapshot, Router} from "@angular/router";

declare var window: any;

@Component({
  selector: 'arb-main',
  template: `
    <div class="ui four column grid">
      <div class="one column row">
        <div class="column">
          <p>You are not logged in</p>
          <button (click)="login()">Click to log in</button>
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./app.scss']
})
export class MainComponent implements OnInit {

  private loginUrl = '/login';

  constructor(public userService: UserService) {

  }

  ngOnInit(): void {
    if (localStorage.getItem(environment.authToken) || this.userService.getTokenFromCookie()) {
      this.userService.logIn();
    }
  }

  public login() {
    window.location = `${environment.authUrl}${this.loginUrl}`;
  }
}
