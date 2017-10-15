import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {environment} from '../../../environments/environment';

declare var window: any;


@Injectable()
export class RouteService {

  constructor(private router: Router) {

  }

  public goToLoginPage() {
    this.router.navigate([environment.client.loginUrl]);
  }

  public goToDashboard() {
    this.router.navigate([environment.client.dashboard.url]);
  }

  public goToTournamentForm() {
    this.router.navigate([`${environment.client.dashboard.managementPanelUrl}/new`])
  }

  public goToLoginServerPage() {
    window.location = `${environment.server.auth.loginUrl}`;
  }

  public goToTournamentDetails(id: string) {
    this.router.navigate([environment.client.dashboard.tournamentUrl, id])
  }

  public goToTournamentResults(tournamentId: string) {
    console.log('Click click, to do me')
  }
}
