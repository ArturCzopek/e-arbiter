import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptions, RequestOptionsArgs, XHRBackend} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Response} from '@angular/http/src/static_response'
import {RouteService} from './route.service';
import {AuthService} from './auth.service';
import {environment} from '../../../environments/environment';
import {UserStorage} from './user.storage';

@Injectable()
export class ArbiterHttpService extends Http {

  constructor(backend: XHRBackend, defaultOptions: RequestOptions,
              private userStorage: UserStorage, private routeService: RouteService) {
    super(backend, defaultOptions);
  }

  get(url: string, requestOptionsArgs?: RequestOptionsArgs): Observable<Response> {
    return super.get(url, this.prepareAuthOptions(requestOptionsArgs))
      .catch(error => this.handleBlockedUser(error));
  }

  post(url: string, body: any = {}): Observable<Response> {
    return super.post(url, body, this.prepareAuthOptions())
      .catch(error => this.handleBlockedUser(error));
  }

  put(url: string, body: any = {}): Observable<Response> {
    return super.put(url, body, this.prepareAuthOptions())
      .catch(error => this.handleBlockedUser(error));
  }

  private handleBlockedUser(error) {
    if (error.status === AuthService.disabledUserStatus) { // status for disabled user
      this.userStorage.clearUser();
      this.routeService.goToLoginPage();
    }

    return Observable.throw(error);
  }

  private prepareAuthOptions(requestOptionsArgs?: RequestOptionsArgs): RequestOptions {
    const headers = new Headers();
    headers.append(environment.authToken, localStorage.getItem(environment.authToken));
    const options = new RequestOptions();
    options.headers = headers;

    if (requestOptionsArgs && requestOptionsArgs.responseType) {
      options.responseType = requestOptionsArgs.responseType;
    }

    return options;
  }
}
