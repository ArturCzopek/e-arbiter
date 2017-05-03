import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserService} from "./user.service";
import {environment} from "../environments/environment";

declare var window: any;

@Component({
  selector: 'arb-login',
  template: `
    <p *ngIf="!userService.getLoggedInUser()">You are not logged in</p>
    <button *ngIf="!userService.getLoggedInUser()" (click)="login()">Click to log in</button>
  `,
  styleUrls: ['./app.component.css']
})
export class MainComponent implements OnInit {

  private loginUrl = '/login';

  constructor(private route: ActivatedRoute, public userService: UserService) {

  }

  ngOnInit(): void {
    this.userService.logIn(this.route.snapshot.params[environment.authToken]);
  }

  public login() {
    window.location = `${environment.authUrl}${this.loginUrl}`;
  }
}
