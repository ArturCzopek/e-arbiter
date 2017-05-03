import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {UserService} from "./user.service";
import {environment} from "../environments/environment";

@Injectable()
export class AuthGuard implements CanActivate {

  private mainUrl: string = '/main';

  constructor(private router: Router, private userService: UserService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    if (localStorage.getItem(environment.authToken) && this.userService.getLoggedInUser()) {
      return true;
    }

    alert('Log in first!');
    this.router.navigate([this.mainUrl]);
    return false;
  }

}
