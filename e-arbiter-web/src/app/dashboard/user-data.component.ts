import {Component} from "@angular/core";
import {UserService} from "../shared/service/user.service";
@Component({
  selector: 'arb-user-data',
  template: `
    <div *ngIf="userService.getLoggedInUser()">
      <h1>Zalogowano jako {{userService.getLoggedInUser().name}} (id: {{userService.getLoggedInUser().id}})</h1>
      <div class="ui buttons">
        <button (click)="getUserFromOtherModule()" class="ui black inverted button">Weż użytkownika poprzez
          executora (sprawdź konsolę)
        </button>
        <div class="or"></div>
        <button *ngIf="userService.getLoggedInUser()" (click)="userService.logOut()"
                class="ui black inverted positive button">Wyloguj się
        </button>
      </div>
    </div>`,
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
