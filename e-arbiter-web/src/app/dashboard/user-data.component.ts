import {Component} from "@angular/core";
import {AuthService} from "../shared/service/auth.service";
@Component({
  selector: 'arb-user-data',
  template: `
    <div *ngIf="authService.getLoggedInUser()">
      <h1>Zalogowano jako {{authService.getLoggedInUser().name}} (id: {{authService.getLoggedInUser().id}})</h1>
      <div class="ui buttons">
        <button (click)="getUserFromOtherModule()" class="ui black inverted button">Weż użytkownika poprzez
          executora (sprawdź konsolę)
        </button>
        <div class="or"></div>
        <button *ngIf="authService.getLoggedInUser()" (click)="authService.logOut()"
                class="ui black inverted positive button">Wyloguj się
        </button>
      </div>
      <div class="ui buttons">
        <button (click)="executeSampleCode()" class="ui black inverted button">
          Uruchom executora (sprawdź konsolę)
        </button>
        <button (click)="getMeInfo()" class="ui black inverted button">/me (console)</button>
      </div>

    </div>
    <tournament-form></tournament-form>`,
  styleUrls: ['./dashboard.scss']
})
export class UserDataComponent {

  constructor(public authService: AuthService) {

  }

  public getUserFromOtherModule() {
    this.authService.getUserFromServerUsingOtherModule().first().subscribe(
      user => console.log(user)
    )
  }

  public executeSampleCode() {
    this.authService.executeSampleCode().first().subscribe(
      res => console.log(res)
    )
  }

  public getMeInfo() {
    this.authService.getMeInfo()
      .first()
      .subscribe(
        res => console.log(res)
      )
  }
}
