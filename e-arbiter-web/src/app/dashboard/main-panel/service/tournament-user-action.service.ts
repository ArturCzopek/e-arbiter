import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {environment} from '../../../../environments/environment';
import {TournamentUserActionRequest} from '../model/tournament-user-action.model';

@Injectable()
export class TournamentUserActionService {

  constructor(private http: Http) {
  }

  public joinToTournament(joinRequest: TournamentUserActionRequest): Observable<any> {
    return this.http.post(`${environment.server.tournament.userActionTournamentUrl}/join`, joinRequest);
  }

  public leaveTournament(leaveRequest: TournamentUserActionRequest): Observable<any> {
    return this.http.post(`${environment.server.tournament.userActionTournamentUrl}/leave`, leaveRequest);
  }
}
