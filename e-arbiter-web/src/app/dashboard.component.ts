import {Component} from "@angular/core";
import {UserService} from "./user.service";

@Component({
  selector: 'arb-dashboard',
  template: `
    <p *ngIf="userService.getLoggedInUser()">Logged by id {{userService.getLoggedInUser().id}} as
      {{userService.getLoggedInUser().name}}</p>
    <p>{{userService.getMessage()}}</p>
    <button (click)="getUserFromOtherModule()">Get user by other service (check console)</button>
    <button *ngIf="userService.getLoggedInUser()" (click)="userService.logOut()">Logout</button>
  `,
  styleUrls: ['./app.component.css']
})
export class DashboardComponent {

  constructor(public userService: UserService) {

  }

  public getUserFromOtherModule() {
    this.userService.getUserFromServerUsingOtherModule().first().subscribe(
      user => console.log(user)
    )
  }


}
