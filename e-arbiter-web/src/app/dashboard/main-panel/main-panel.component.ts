import {Component, OnInit} from "@angular/core";
import {TournamentStatus} from "../../shared/interface/tournament-status.enum";
import {TournamentPreviewService} from "../../shared/service/tournament-preview.service";
import {TournamentPreview} from "../../shared/interface/tournament-preview.interface";
import {EMPTY_PAGE, Page} from "../../shared/interface/page.interface";

@Component({
  selector: 'arb-main-panel',
  template: `
    <div class="ui container center aligned scrollable-page-view">
        <arb-main-panel-menu
          [tournamentsStatus]="tournamentsStatus" 
          [query]="query" 
          [pageSize]="pageSize" 
          (onStatusChange)="changeStatus($event)" 
          (onSearch)="findTournaments($event)"
        ></arb-main-panel-menu>
        <div *ngFor="let tournament of tournamentsPage?.content">
            <div>
                <p>{{tournament | json}}</p>
            </div>
        </div>
    </div>`
})
export class MainPanelComponent implements OnInit {

  public pageNumber: number = 1;
  public pageSize: number = 5;
  public query: string = "";
  public tournamentsStatus = TournamentStatus.ACTIVE;
  public tournamentsPage: Page<TournamentPreview> = EMPTY_PAGE;

  constructor(private tournamentPreviewService: TournamentPreviewService) {
  }

  ngOnInit(): void {
    this.loadProperTournaments();
  }

  public changeStatus(tournamentStatus: TournamentStatus) {
    this.tournamentsStatus = tournamentStatus;
    this.query = "";
    this.loadProperTournaments();
  }

  public findTournaments(queryData: any) {
    const {pageSize, query} = queryData;
    this.pageSize = pageSize;
    this.query = query;
    this.loadProperTournaments();
  }

  private loadProperTournaments() {
    if (this.tournamentsStatus === TournamentStatus.ACTIVE) {
      this.loadActiveTournaments()
    } else {
      this.loadClosedTournaments();
    }
  }

  private loadActiveTournaments(): void {
    this.tournamentPreviewService.getUserActiveTournaments(this.pageNumber, this.pageSize, this.query)
      .subscribe(
        page => this.tournamentsPage = page,
        error => console.log("Cannot load tournaments")
      );
  }

  private loadClosedTournaments(): void {
    this.tournamentPreviewService.getUserClosedTournaments(this.pageNumber, this.pageSize, this.query)
      .subscribe(
        page => this.tournamentsPage = page,
        error => console.log("Cannot load tournaments")
      );
  }
}
