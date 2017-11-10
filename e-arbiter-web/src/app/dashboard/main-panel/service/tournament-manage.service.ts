import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {AuthService} from '../../../shared/service/auth.service';
import {Observable} from 'rxjs/Observable';
import {environment} from '../../../../environments/environment';
import {User} from "../../../shared/interface/user.interface";

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

  public getEnrolledUsers(id: string): Observable<User[]> {
    return this.http.get(`${environment.server.tournament.managementTournamentsUrl}/${id}/enrolled-users`,
      this.authService.prepareAuthOptions()
    )
      .map(res => res.json());
  }

  public getBlockedUsers(id: string): Observable<User[]> {
    return this.http.get(`${environment.server.tournament.managementTournamentsUrl}/${id}/blocked-users`,
      this.authService.prepareAuthOptions()
    )
      .map(res => res.json());
  }

  public blockUserInTournament(tournamentId: string, userId: number): Observable<any> {
    return this.http.put(`${environment.server.tournament.managementTournamentsUrl}/${tournamentId}/block-user/${userId}`,
      {},
      this.authService.prepareAuthOptions()
    );
  }

  public unblockUserInTournament(tournamentId: string, userId: number): Observable<any> {
    return this.http.put(`${environment.server.tournament.managementTournamentsUrl}/${tournamentId}/unblock-user/${userId}`,
      {},
      this.authService.prepareAuthOptions()
    );
  }
}
