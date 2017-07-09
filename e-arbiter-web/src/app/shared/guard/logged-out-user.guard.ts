import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "../service/auth.service";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs/Observable";
import "rxjs/observable/of";

@Injectable()
export class LoggedOutUserGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):Observable<boolean>|boolean {

    if (!this.authService.getLoggedInUser() && !localStorage.getItem(environment.authToken)) {
      return true;
    }

    console.log('Logout failed!', this.authService, localStorage.getItem(environment.authToken));

    this.router.navigate([environment.client.dashboard.url]);
    return false;
  }

}
