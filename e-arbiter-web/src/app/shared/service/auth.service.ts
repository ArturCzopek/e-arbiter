import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions} from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/add/operator/first";
import "rxjs/add/operator/catch";
import "rxjs/add/observable/throw";
import {Observable} from "rxjs/Observable";
import {environment} from "../../../environments/environment";
import {User} from "../interface/user.interface";
import {ActivatedRoute} from "@angular/router";

declare var window: any;

@Injectable()
export class AuthService {

  private user: User = null;

  constructor(private http: Http) {}

  public logIn() {

    const token = this.getTokenFromLocalStorage();

    // user is not logged in but we can do it with token which is in his browser
    if (!this.isLoggedInUser() && token) {

      // we need to check if token is in localstorage
      // it has to be there, even if user has token from redirect (login component will set it in storage)
      const token = this.getTokenFromLocalStorage();

      if (token) {                          // we want to log new/earlier user
        this.setToken(token);               // maybe it is new token, so (re)set
        this.getUserFromServer();           // try to log in user with token from storage
      }

      // No token, user have to log in by button, after that token will be returned and set in storage
      // No action for now
    }
  }

  public logOut(): any {
    this.http.post(environment.server.auth.logoutGatewayUrl, {}, this.prepareAuthOptions())
      .catch(this.handleFailLogout)
      .first()
      .subscribe(
        ok => {
          this.clearToken();
          this.user = null;
          window.location = environment.server.auth.logoutUrl;
        }
      )
  }

  public prepareAuthOptions(): RequestOptions {
    const headers = new Headers();
    headers.append(environment.authToken, localStorage.getItem(environment.authToken));
    const options = new RequestOptions();
    options.headers = headers;
    return options;
  }

  public getTokenFromRouteParams(route: ActivatedRoute): string {
    return route.snapshot.params[environment.authToken];
  }

  public getTokenFromLocalStorage(): string {
    return localStorage.getItem(environment.authToken);
  }

  public setToken(token: string) {
    return localStorage.setItem(environment.authToken, token);
  }

  public isLoggedInUser(): boolean {
    return !!this.user;
  }

  public getLoggedInUserName(): string {
    return (this.user) ? this.user.name : "Niezalogowany";
  }

  public getMeInfo(): Observable<any> {
    return this.http.get(environment.server.auth.meUrl, this.prepareAuthOptions()).map(res => res.json());
  }

  public getUserImgLink(): string {
    return `${environment.githubUrl}/${this.getLoggedInUserName()}.png`
  };

  public isLoggedInUserAdmin(): boolean {
    return this.user && this.user.roles.some(role => role.name.toUpperCase() === "ADMIN")
  }

  private getUserFromServer(): any {
    this.http.get(`${environment.server.auth.userUrl}`, this.prepareAuthOptions())
      .map(res => res.json())
      .catch(this.handleInvalidToken)      // token is invalid
      .first()
      .subscribe(
        user => {
          this.user = user;
        }
      );
  }

  private clearToken() {
    localStorage.removeItem(environment.authToken);
  }

  private handleInvalidToken() {
    this.clearToken();
    return Observable.throw('Invalid token');
  }

  private handleFailLogout() {
    return Observable.throw('Cannot logout, try again');
  }
}
