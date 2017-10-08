import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Http} from '@angular/http';
import {AuthService} from './auth.service';
import {environment} from 'environments/environment';
import {TournamentDetails} from '../../dashboard/main-panel/interface/tournament-details.interface';

@Injectable()
export class TournamentDetailsService {

  constructor(private http: Http, private authService: AuthService) {
  }

  public getDetailsForTournament(id: string): Observable<TournamentDetails> {
    return this.http.get(
      `${environment.server.tournament.userDetailsTournamentUrl}/${id}`,
      this.authService.prepareAuthOptions()
    )
      .first()
      .map(res => res.json())
      .catch(error => Observable.throw(new Error('Cannot fetch details for this tournament')));
  }
}
