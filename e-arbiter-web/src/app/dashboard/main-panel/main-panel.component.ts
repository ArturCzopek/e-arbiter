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
        <div class="ui active centered loader massive" *ngIf="isLoading; else tournamentsList"></div>
        <ng-template #tournamentsList>
            <arb-tour-prev-card 
              *ngFor="let tournament of tournamentsPage?.content; trackBy: trackById"
              [tournamentPreview]="tournament"
            ></arb-tour-prev-card>
          <div class="ui red message" *ngIf="errorMessage?.length > 0">{{errorMessage}}</div>
        </ng-template>
    </div>`
})
export class MainPanelComponent implements OnInit {

  public pageNumber: number = 1;
  public pageSize: number = 5;
  public query: string = "";
  public tournamentsStatus = TournamentStatus.ACTIVE;
  public tournamentsPage: Page<TournamentPreview> = EMPTY_PAGE;
  public isLoading = true;
  public errorMessage: string = "";

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

  public trackById(index: number, tournament: TournamentPreview): string {
    return tournament.id;
  }

  private loadProperTournaments() {
    if (this.tournamentsStatus === TournamentStatus.ACTIVE) {
      this.loadActiveTournaments()
    } else {
      this.loadClosedTournaments();
    }
  }

  private loadActiveTournaments(): void {
    this.isLoading = true;

    this.tournamentPreviewService.getUserActiveTournaments(this.pageNumber, this.pageSize, this.query)
      .subscribe(
        page => {
          this.errorMessage = "";
          this.tournamentsPage = page;
        },
        error => {
            this.errorMessage = "Nie udało się załadować turniejów. Spróbuj jeszcze raz. W razie dalszych problemów, skontaktuj się z administratorem.";
            this.tournamentsPage = EMPTY_PAGE;
        },
        () => this.isLoading = false
      );
  }

  private loadClosedTournaments(): void {
    this.tournamentPreviewService.getUserClosedTournaments(this.pageNumber, this.pageSize, this.query)
      .subscribe(
        page => {
          this.errorMessage = "";
          this.tournamentsPage = page;
        },
        error => {
          this.errorMessage = "Nie udało się załadować turniejów. Spróbuj jeszcze raz. W razie dalszych problemów, skontaktuj się z administratorem.";
          this.tournamentsPage = EMPTY_PAGE;
        },
        () => this.isLoading = false
      );
  }
}
