import {Component} from "@angular/core";
import {UserService} from "../shared/service/user.service";
@Component({
  selector: 'arb-user-data',
  template: `
    <p *ngIf="userService.getLoggedInUser()">Logged by id {{userService.getLoggedInUser().id}} as
      {{userService.getLoggedInUser().name}}</p>
    <button (click)="getUserFromOtherModule()">Get user by other service (check console)</button>
    <button *ngIf="userService.getLoggedInUser()" (click)="userService.logOut()">Logout</button>
  `,
  styleUrls: ['./dashboard.scss']
})
export class UserDataComponent {

  constructor(public userService: UserService) {

  }

  public getUserFromOtherModule() {
    this.userService.getUserFromServerUsingOtherModule().first().subscribe(
      user => console.log(user)
    )
  }


}
