import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {UserService} from "../service/user.service";
import {environment} from "../../../environments/environment";

@Injectable()
export class LoggedInUserGuard implements CanActivate {

  private mainUrl: string = '/main';

  constructor(private router: Router, private userService: UserService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    if (this.userService.getLoggedInUser() !== null) {
      return true;
    }

    console.log('Log in first!');
    this.router.navigate([this.mainUrl]);
    return false;
  }

}
