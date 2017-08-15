import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";

declare var window: any;


@Injectable()
export class RouteService {

  constructor(private router: Router) {

  }

  public goToLoginPage() {
    this.router.navigate([environment.client.mainUrl]);
  }

  public goToDashboard() {
    this.router.navigate([environment.client.dashboard.url]);
  }

  public goToLoginServerPage() {
    window.location = `${environment.server.auth.loginUrl}`;
  }
}
