import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {UserService} from "../service/user.service";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs/Observable";
import "rxjs/observable/of";

@Injectable()
export class LoggedOutUserGuard implements CanActivate {

  constructor(private router: Router, private userService: UserService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):Observable<boolean>|boolean {

    if (!this.userService.getLoggedInUser() && !localStorage.getItem(environment.authToken)) {
      return true;
    }

    console.log('Logout failed!', this.userService, localStorage.getItem(environment.authToken));

    this.router.navigate([environment.client.dashboard.url]);
    return false;
  }

}
