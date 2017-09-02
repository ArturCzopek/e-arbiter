import {Component} from "@angular/core";
import {AuthService} from "../../shared/service/auth.service";
import {Http} from "@angular/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs/Observable";
import "rxjs/add/observable/of";

@Component({
  selector: 'arb-dvlp',
  template: `
    <div class="ui container center aligned scrollable-page-view" *ngIf="authService.isLoggedInUser()">
      <h4>Zalogowano jako {{authService.getLoggedInUserName()}}</h4>
      <div class="ui buttons">
        <button (click)="executeBlockedInnerRequest()" class="ui red medium button">
            Uruchom inner request (powinien byc zablokowany, sorry)
        </button>
        <button (click)="getMeInfo()" class="ui teal medium button">/me (console)</button>
      </div>
    </div>`
})
export class DevelopmentCardComponent {

  constructor(public authService: AuthService, private http: Http) {

  }

  public executeBlockedInnerRequest() {
    this.http
    // existing endpoint
      .get(`${environment.server.api.url}/auth/inner/user/name/1`, this.authService.prepareAuthOptions())
      .map(res => res.json())
      .catch((e) => {
        console.log(`Tak miało być, catched, blocked, not found`);
        return Observable.of(e);
      })
      .first()
      .subscribe();
  }

  public getMeInfo() {
    this.authService.getMeInfo()
      .first()
      .subscribe(
        res => console.log(res)
      )
  }
}
