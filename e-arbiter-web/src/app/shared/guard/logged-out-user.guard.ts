import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {UserService} from "../service/user.service";
import {environment} from "../../../environments/environment";

@Injectable()
export class LoggedOutUserGuard implements CanActivate {

  private dashboardUrl: string = '/dashboard';

  constructor(private router: Router, private userService: UserService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    if (!this.userService.getLoggedInUser() && !localStorage.getItem(environment.authToken)) {
      return true;
    }

    console.log('Logout failed!', this.userService, localStorage.getItem(environment.authToken));

    this.router.navigate([this.dashboardUrl]);
    return false;
  }

}
