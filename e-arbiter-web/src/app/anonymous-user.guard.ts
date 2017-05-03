import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {UserService} from "./user.service";
import {environment} from "../environments/environment";

@Injectable()
export class AnonymousUserGuard implements CanActivate {

  private dashboardUrl: string = '/dashboard';

  constructor(private router: Router, private userService: UserService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    if (!this.userService.getLoggedInUser() && !localStorage.getItem(environment.authToken)) {
      return true;
    }

    alert('Logout failed!');

    this.router.navigate([this.dashboardUrl]);
    return false;
  }

}
