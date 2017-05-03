import {Injectable} from "@angular/core";
import {Http, RequestOptions, Headers} from "@angular/http";
import "rxjs/add/operator/map";
import "rxjs/add/operator/first";
import "rxjs/add/operator/catch";
import "rxjs/add/observable/throw";
import {Observable} from "rxjs/Observable";
import {Router} from "@angular/router";
import {environment} from "../environments/environment";
import {User} from "./user.model";

declare var window: any;

@Injectable()
export class UserService {
  private user: User;
  private message: string;
  private tokenUrl: string = '/api/token';
  private userUrl: string = '/api/user';
  private logoutUrl: string = '/github/logout';
  private dashboardUrl: string = '/dashboard';

  constructor(private http: Http, private router: Router) {
    this.message = '';
  }

  public logIn(oauth_token: string) {

    if (this.user) {
      return;
    }

    // if we want to log in user, we use received token,
    // otherwise, we check if user was logged in earlier by getting token from storage
    const token = oauth_token || localStorage.getItem(environment.authToken);

    // we want to log new/earlier user
    if (token) {
      // maybe it is new token, so (re)set
      localStorage.setItem(environment.authToken, token);

      // try to log in user with token from storage
      this.getUserFromServer();
    } else {
      // No token (maybe storage was cleaned by user), check if it's on server
      this.getToken()
        .map(res => res.json())
        // no token, so user have to log in
        .catch(this.handleMissingToken)
        .first()
        .subscribe(
          token => {
            this.message = 'got token, there must be a problem with local storage';
            localStorage.setItem(environment.authToken, token);
            this.getUserFromServer();
          }
        );
    }
  }

  public getLoggedInUser() {
    return this.user;
  }

  public getMessage() {
    return this.message;
  }

  public getUserFromServerUsingOtherModule(): Observable<any> {
    return this.http.get(`${environment.proxyUrl}/exec/api/execute`, this.prepareAuthOptions()).map(res => res.json());
  }

  public logOut() {
    localStorage.removeItem(environment.authToken);
    this.user = null;
    window.location = `${environment.authUrl}${this.logoutUrl}`;
  }

  private getUserFromServer(): any {
    this.http.get(`${environment.authUrl}${this.userUrl}`, this.prepareAuthOptions())
      .map(res => res.json())
      // token is invalid
      .catch(this.handleInvalidToken)
      .first()
      .subscribe(
        user => {
          this.user = user;
          this.message = 'logged In';
          this.router.navigate([this.dashboardUrl]);
        }
      );
  }

  private getToken(): Observable<any> {
    return this.http.get(`${environment.authUrl}${this.tokenUrl}`);
  }

  private handleInvalidToken() {
    this.message = 'Wrong token!';
    localStorage.removeItem(environment.authToken);
    this.router.navigate(['/']);
    return Observable.throw('invalid token');
  }

  private handleMissingToken() {
    this.message = 'Not found token, log in by button';
    return Observable.throw('Not found token, log in by button');
  }

  private prepareAuthOptions(): RequestOptions {
    const headers = new Headers();
    headers.append("oauth_token", localStorage.getItem(environment.authToken));
    const options = new RequestOptions();
    options.headers = headers;
    return options;
  }
}
