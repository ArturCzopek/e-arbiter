import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {AuthService} from '../../../shared/service/auth.service';
import {Observable} from 'rxjs/Observable';
import {environment} from '../../../../environments/environment';
import {TournamentUserActionRequest} from '../model/tournament-user-action.model';

@Injectable()
export class TournamentUserActionService {

  constructor(private http: Http, private authService: AuthService) {}

  public joinToTournament(joinRequest: TournamentUserActionRequest): Observable<any> {
    return this.http.post(
      `${environment.server.tournament.userActionTournamentUrl}/join`,
      joinRequest,
      this.authService.prepareAuthOptions()
    );
  }

  public leaveTournament(joinRequest: TournamentUserActionRequest): Observable<any> {
    return this.http.post(
      `${environment.server.tournament.userActionTournamentUrl}/leave`,
      joinRequest,
      this.authService.prepareAuthOptions()
    );
  }
}
