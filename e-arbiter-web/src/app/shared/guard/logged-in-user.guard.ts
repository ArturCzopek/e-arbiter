import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "../service/auth.service";
import {Observable} from "rxjs/Observable";
import {environment} from "../../../environments/environment";

@Injectable()
export class LoggedInUserGuard implements CanActivate {

  constructor(private router: Router, private userService: AuthService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):Observable<boolean>|boolean {

    if (this.userService.getLoggedInUser() !== null) {
      return true;
    }

    this.router.navigate([environment.client.mainUrl]);
    return false;
  }

}
