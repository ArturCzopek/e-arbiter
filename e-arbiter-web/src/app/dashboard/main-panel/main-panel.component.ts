import {Component} from "@angular/core";
import {TournamentStatus} from "../../shared/interface/tournament-status.enum";

@Component({
  selector: 'arb-main-panel',
  template: `
    <div class="ui container center aligned scrollable-page-view">
        <arb-main-panel-menu
          [tournamentsStatus]="tournamentsStatus" 
          [query]="query" 
          [tournamentsPerPage]="tournamentsPerPage" 
          (onStatusChange)="changeStatus($event)" 
          (onSearch)="findTournaments($event)"
        ></arb-main-panel-menu>
    </div>`
})
export class MainPanelComponent {

  public pageNr: number = 1;
  public tournamentsPerPage: number = 5;
  public query: string = "";
  public tournamentsStatus = TournamentStatus.ACTIVE;

  public changeStatus(tournamentStatus: TournamentStatus) {
    console.log("tab", tournamentStatus);
    this.tournamentsStatus = tournamentStatus;
  }

  public findTournaments(queryData: any) {
    console.log(queryData);
    this.tournamentsPerPage = queryData.tournamentsPerPage;
    this.query = queryData.query;
  }
}
