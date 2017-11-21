import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {UserTournamentResults} from '../interface/user-tournament-results.interface';
import {Observable} from 'rxjs/Observable';
import {environment} from '../../../../environments/environment';

@Injectable()
export class ResultService {

  constructor(private http: Http) {
  }

  public getResults(tournamentId: string): Observable<UserTournamentResults[]> {
    return this.http.get(`${environment.server.tournament.resultsUrl}/${tournamentId}`)
      .map(res => res.json());
  }
}
