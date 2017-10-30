import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {AuthService} from '../../../shared/service/auth.service';
import {Observable} from 'rxjs/Observable';
import {environment} from '../../../../environments/environment';

@Injectable()
export class TournamentManageService {

  constructor(private http: Http, private authService: AuthService) {
  }

  public activateTournament(id: string): Observable<any> {
    return this.http.put(`${environment.server.tournament.managementTournamentsUrl}/${id}/activate`,
      {},
      this.authService.prepareAuthOptions()
    );
  }

  public deleteTournament(id: string): Observable<any> {
    return this.http.put(`${environment.server.tournament.managementTournamentsUrl}/${id}/delete`,
      {},
      this.authService.prepareAuthOptions()
    );
  }

  public extendDeadline(id: string, seconds: number): Observable<any> {
    return this.http.put(`${environment.server.tournament.managementTournamentsUrl}/${id}/extend/${seconds}`,
      {},
      this.authService.prepareAuthOptions()
    );
  }
}
