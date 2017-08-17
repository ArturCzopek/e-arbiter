import {Component} from "@angular/core";
import {AuthService} from "../../shared/service/auth.service";

@Component({
  selector: 'arb-dvlp',
  template: `
    <div class="ui container center aligned scrollable-page-view" *ngIf="authService.getLoggedInUser()">
      <h4>Zalogowano jako {{authService.getLoggedInUser().name}} (id: {{authService.getLoggedInUser().id}})</h4>
      <div class="ui buttons">

        <button (click)="getMeInfo()" class="ui teal medium button">/me (console)</button>
      </div>
    </div>`
})
export class DevelopmentCardComponent {

  constructor(public authService: AuthService) {

  }

  public getMeInfo() {
    this.authService.getMeInfo()
      .first()
      .subscribe(
        res => console.log(res)
      )
  }
}
