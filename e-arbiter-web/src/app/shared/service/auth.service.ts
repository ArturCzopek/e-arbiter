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

  constructor(private http: Http) {

  }

  public logIn() {

    if (this.user) {
      return;
    }

    // we need to check if token is in localstorage
    // it has to be there, even if user has token from redirect (login component will set it in storage)
    const token = this.getTokenFromLocalStorage();


    if (token) {                          // we want to log new/earlier user
      this.setToken(token);               // maybe it is new token, so (re)set
      this.getUserFromServer();           // try to log in user with token from storage
    } else {                              // No token (maybe storage was cleaned by user), check if it's on server
      this.getTokenFromServer()
        .map(res => res.json())
        .catch(this.handleMissingToken)   // no token, so user have to log in by clicking log in button and redirecting to server
        .first()
        .subscribe(
          token => {
            this.setToken(token);         // there is a valid token for this user so we can try to get his data from server
            this.getUserFromServer();
          }
        );
    }
  }

  public logOut() {
    this.clearToken();
    this.user = null;
    window.location = environment.server.auth.logoutUrl;
  }

  public prepareAuthOptions(): RequestOptions {
    const headers = new Headers();
    headers.append(environment.authToken, localStorage.getItem(environment.authToken));
    const options = new RequestOptions();
    options.headers = headers;
    return options;
  }

  public getTokenFromRouteParams = (route: ActivatedRoute): string => route.snapshot.params[environment.authToken];

  public getTokenFromLocalStorage = (): string => localStorage.getItem(environment.authToken);

  public setToken = (token: string) => localStorage.setItem(environment.authToken, token);

  public getLoggedInUser = (): User => this.user;

  public getMeInfo = (): Observable<any> => this.http.get(environment.server.auth.meUrl, this.prepareAuthOptions()).map(res => res.json());

  public getUserImgLink = (): string => `${environment.githubUrl}/${this.getLoggedInUser().name}.png`;

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

  private getTokenFromServer = (): Observable<any> => this.http.get(`${environment.server.auth.tokenUrl}`);

  private clearToken = () => localStorage.removeItem(environment.authToken);

  private handleInvalidToken() {
    this.clearToken();
    return Observable.throw('Invalid token');
  }

  private handleMissingToken = () => Observable.throw('Not found token, log in by button');
}
