import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {environment} from 'environments/environment';
import {TournamentDetails} from '../../dashboard/main-panel/interface/tournament-details.interface';
import {Http} from '@angular/http';

@Injectable()
export class TournamentDetailsService {

  constructor(private http: Http) {
  }

  public getDetailsForTournament(id: string): Observable<TournamentDetails> {
    return this.http.get(`${environment.server.tournament.userDetailsTournamentUrl}/${id}`)
      .first()
      .map(res => res.json());
  }
}
