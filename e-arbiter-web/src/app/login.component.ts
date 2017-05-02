import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserService} from "./user.service";

declare var window: any;

@Component({
  selector: 'arb-login',
  template: `
    {{message}}  
    <button *ngIf="!userService.getUser()" (click)="login()">Login</button>
  `,
  styleUrls: ['./app.component.css']
})
export class LoginComponent implements OnInit {

  public message: string;

  constructor(private route: ActivatedRoute, public userService: UserService) {

  }

  ngOnInit(): void {
    if (this.route.snapshot.params['oauth_token']) {
      this.userService.logIn();
      this.message = 'loging...';
    } else {
      this.message = 'you can log in by clicking button below';
    }
  }

  public login() {
    window.location = 'http://localhost:8080/auth/';
  }

}
