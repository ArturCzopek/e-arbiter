import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/first';

@Injectable()
export class UserService {
  private user: any;

  constructor(private http: Http) {

  }

  public getUser() {
    return this.user;
  }

  public logIn() {

    if (this.user) {
      return;
    }

    console.log('Logging...');

    this.http.get(
      `http://localhost:8080/auth/api/login?oauth_token=${localStorage.getItem('oauth_token')}`
    ).map(res => res.json()).first().subscribe(
      user => this.user = user
    );
  }
}
