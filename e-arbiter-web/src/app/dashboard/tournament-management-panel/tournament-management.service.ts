import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {AuthService} from "../../shared/service/auth.service";
import {Tournament} from "./interface/tournament.interface";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs/Observable";

@Injectable()
export class TournamentManagementService {

  constructor(private http: Http, private authService: AuthService) {}

  public saveTournament(tournament: Tournament): Observable<Tournament> {
    return this.http.post(environment.server.tournament.saveUrl, tournament,
      this.authService.prepareAuthOptions()).map(res => res.json());
  }

}
