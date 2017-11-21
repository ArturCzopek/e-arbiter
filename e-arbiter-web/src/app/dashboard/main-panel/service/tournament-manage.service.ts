import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {environment} from '../../../../environments/environment';
import {User} from "../../../shared/interface/user.interface";

@Injectable()
export class TournamentManageService {

  constructor(private http: Http) {
  }

  public activateTournament(id: string): Observable<any> {
    return this.http.put(`${environment.server.tournament.managementTournamentsUrl}/${id}/activate`, {});
  }

  public deleteTournament(id: string): Observable<any> {
    return this.http.put(`${environment.server.tournament.managementTournamentsUrl}/${id}/delete`, {});
  }

  public extendDeadline(id: string, seconds: number): Observable<any> {
    return this.http.put(`${environment.server.tournament.managementTournamentsUrl}/${id}/extend/${seconds}`, {});
  }

  public getEnrolledUsers(id: string): Observable<User[]> {
    return this.http.get(`${environment.server.tournament.managementTournamentsUrl}/${id}/enrolled-users`)
      .map(res => res.json());
  }

  public getBlockedUsers(id: string): Observable<User[]> {
    return this.http.get(`${environment.server.tournament.managementTournamentsUrl}/${id}/blocked-users`)
      .map(res => res.json());
  }

  public blockUserInTournament(tournamentId: string, userId: number): Observable<any> {
    return this.http.put(`${environment.server.tournament.managementTournamentsUrl}/${tournamentId}/block-user/${userId}`, {});
  }

  public unblockUserInTournament(tournamentId: string, userId: number): Observable<any> {
    return this.http.put(`${environment.server.tournament.managementTournamentsUrl}/${tournamentId}/unblock-user/${userId}`, {});
  }
}
