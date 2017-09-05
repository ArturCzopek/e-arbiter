import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {TournamentPreview} from "../interface/tournament-preview.interface";
import {AuthService} from "./auth.service";
import {environment} from "../../../environments/environment";
import {Page} from "../interface/page.interface";

@Injectable()
export class TournamentPreviewService {

  constructor(private http: Http, private authService: AuthService) {
  }


  public getUserActiveTournaments(pageNumber: number = 1, pageSize: number = 5, query: string = ""): Observable<Page<TournamentPreview>> {

    return this.http.get(
      `${environment.server.tournament.allTournamentsUrl}/active?page=${pageNumber - 1}&size=${pageSize}&query=${query}`,
      this.authService.prepareAuthOptions()
    )
      .first()
      .map(res => res.json());
  }

  public getUserClosedTournaments(pageNumber: number = 1, pageSize: number = 5, query: string = ""): Observable<Page<TournamentPreview>> {

    return this.http.get(
      `${environment.server.tournament.allTournamentsUrl}/finished?page=${pageNumber - 1}&size=${pageSize}&query=${query}`,
      this.authService.prepareAuthOptions()
    )
      .first()
      .map(res => res.json());
  }
}
