import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/first';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import {Observable} from 'rxjs/Observable';
import {environment} from '../../../environments/environment';
import {User} from '../interface/user.interface';
import {ActivatedRoute} from '@angular/router';
import {Subject} from 'rxjs/Subject';
import {UserStorage} from "./user.storage";

declare var window: any;

@Injectable()
export class AuthService {
  static get disabledUserStatus(): number {
    return this._disabledUserStatus;
  }

  private static _disabledUserStatus = 423;

  private userDisabled: Subject<string> = new Subject();

  constructor(private http: Http, private userStorage: UserStorage) {
  }

  public logIn() {

    let token = this.getTokenFromLocalStorage();

    // user is not logged in but we can do it with token which is in his browser
    if (!this.isLoggedInUser() && token) {

      // we need to check if token is in localstorage
      // it has to be there, even if user has token from redirect (login component will set it in storage)
      token = this.getTokenFromLocalStorage();

      if (token) {                          // we want to log new/earlier user
        this.setToken(token);               // maybe it is new token, so (re)set
        this.getUserFromServer();           // try to log in user with token from storage
      }

      // No token, user have to log in by button, after that token will be returned and set in storage
      // No action for now
    }
  }

  public logOut(): any {
    this.http.post(environment.server.auth.logoutGatewayUrl, {})
      .catch(this.handleFailLogout.bind(this))
      .first()
      .subscribe(
        ok => {
          this.clearToken();
          this.userStorage.clearUser();
          window.location = environment.server.auth.logoutUrl;
        }
      )
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
    return !!this.userStorage.getUser();
  }

  public getLoggedInUserName(): string {
    const user = this.userStorage.getUser();
    return (user) ? user.name : 'Niezalogowany';
  }

  public getMeInfo(): Observable<any> {
    return this.http.get(environment.server.auth.meUrl).map(res => res.json());
  }

  public getUserImgLink(): string {
    return this.getUserImgLinkByName(this.getLoggedInUserName());
  };

  public getUserImgLinkByName(name: string) {
    return `${environment.githubUrl}/${name}.png`;
  }

  public isLoggedInUserAdmin(): boolean {
    const user = this.userStorage.getUser();
    return user && user.roles.some(role => role.name.toUpperCase() === 'ADMIN')
  }

  public getUserDisabledStream(): Subject<string> {
    return this.userDisabled;
  }

  private getUserFromServer(): any {
    this.http.get(`${environment.server.auth.userUrl}`)
      .map(res => res.json())
      .first()
      .subscribe(
        user => {
          this.userStorage.setUser(user);
        },
        error => {
          this.clearToken();
          if (error.status === AuthService.disabledUserStatus) { // status for disabled user
            this.userDisabled.next(JSON.parse(error._body).message);
          }
        }
      );
  }

  private clearToken() {
    localStorage.removeItem(environment.authToken);
  }

  private handleFailLogout() {
    return Observable.throw('Cannot logout, try again');
  }
}
