import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, RouterStateSnapshot} from '@angular/router';
import {Injectable} from '@angular/core';
import {AuthService} from '../service/auth.service';
import {Observable} from 'rxjs/Observable';
import {RouteService} from '../service/route.service';

@Injectable()
export class LoggedInUserGuard implements CanActivate, CanActivateChild {

  constructor(private routeService: RouteService, private authService: AuthService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
    return this.checkIfCanActivatePageForLoggedInUser();
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.checkIfCanActivatePageForLoggedInUser();
  }

  private checkIfCanActivatePageForLoggedInUser(): boolean {
    if (this.authService.isLoggedInUser()) {
      return true;
    }

    this.routeService.goToLoginPage();
    return false;
  }
}
