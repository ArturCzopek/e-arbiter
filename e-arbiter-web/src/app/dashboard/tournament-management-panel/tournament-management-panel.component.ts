import {Component, OnInit} from '@angular/core';
import {EMPTY_PAGE, Page} from '../../shared/interface/page.interface';
import {TournamentStatus} from '../../shared/interface/tournament-status.enum';
import {TournamentPreview} from '../../shared/interface/tournament-preview.interface';
import {TournamentPreviewService} from '../../shared/service/tournament-preview.service';
import {MenuElement} from '../../shared/model/menu-element.model';
import {RouteService} from '../../shared/service/route.service';

@Component({
  selector: 'arb-tournament-management-panel',
  template: `
    <div class="ui container center aligned scrollable-page-view">
      <arb-menu
        [menuElements]="menuElements"
        [query]="query"
        [pageSize]="pageSize"
        (onTabChange)="changeStatus($event)"
        (onSearch)="findTournaments($event)"
      ></arb-menu>
      <div class="ui active centered loader massive" *ngIf="isLoading; else tournamentsList"></div>
      <ng-template #tournamentsList>
        <arb-tour-prev-card
          *ngFor="let tournament of tournamentsPage?.content; trackBy: trackById"
          [tournamentPreview]="tournament"
        ></arb-tour-prev-card>
        <div class="ui yellow message" *ngIf="tournamentsPage?.content.length === 0 && errorMessage.length === 0">
          Wygląda na to, że nie ma turniejów spełniających Twoje kryteria
        </div>
        <div class="ui red message" *ngIf="errorMessage.length > 0">
          {{errorMessage}}
        </div>
        <div class="main-button-container">
          <button class="ui teal huge button" (click)="goToTournamentForm()" type="button">Stwórz nowy turniej</button>
        </div>
        <arb-pagination [page]="tournamentsPage" (onPageChange)="changePage($event)"></arb-pagination>
      </ng-template>
    </div>
  `
})
export class TournamentManagementPanelComponent implements OnInit {

  public currentPage = 1;
  public pageSize = 5;
  public query = '';
  public tournamentsStatus = TournamentStatus.DRAFT;
  public tournamentsPage: Page<TournamentPreview> = EMPTY_PAGE;
  public isLoading = true;
  public errorMessage = '';

  public menuElements = [
    new MenuElement('Szkice', TournamentStatus.DRAFT),
    new MenuElement('Aktywne', TournamentStatus.ACTIVE),
    new MenuElement('Zakończone', TournamentStatus.FINISHED),
  ]

  constructor(private tournamentPreviewService: TournamentPreviewService, private routeService: RouteService) {
  }

  ngOnInit(): void {
    this.loadProperTournaments();
  }

  public changeStatus(tournamentStatus: TournamentStatus) {
    this.tournamentsStatus = tournamentStatus;
    this.query = '';
    this.currentPage = 1;
    this.loadProperTournaments();
  }

  public changePage(pageNumber: number) {
    this.currentPage = pageNumber;
    this.loadProperTournaments();
  }

  public findTournaments(queryData: any) {
    const {pageSize, query} = queryData;

    if (pageSize !== this.pageSize || query !== this.query) {
      this.currentPage = 1;
    }

    this.pageSize = pageSize;
    this.query = query;
    this.loadProperTournaments();
  }

  public trackById(index: number, tournament: TournamentPreview): string {
    return tournament.id;
  }

  public goToTournamentForm() {
    this.routeService.goToTournamentForm();
  }

  private loadProperTournaments() {
    if (this.tournamentsStatus === TournamentStatus.DRAFT) {
      this.loadDraftTournaments();
    } else if (this.tournamentsStatus === TournamentStatus.ACTIVE) {
      this.loadActiveTournaments();
    } else {
      this.loadFinishedTournaments();
    }
  }

  private loadDraftTournaments(): void {
    this.isLoading = true;

    this.tournamentPreviewService.getDraftManageTournaments(this.currentPage, this.pageSize, this.query)
      .subscribe(
        page => {
          this.errorMessage = '';
          this.tournamentsPage = page;
        },
        error => {
          this.errorMessage = 'Nie udało się załadować turniejów. Spróbuj jeszcze raz. W razie dalszych problemów, skontaktuj się z administratorem.';
          this.tournamentsPage = EMPTY_PAGE;
          this.isLoading = false;
        },
        () => this.isLoading = false
      );
  }

  private loadActiveTournaments(): void {
    this.isLoading = true;

    this.tournamentPreviewService.getActiveManageTournaments(this.currentPage, this.pageSize, this.query)
      .subscribe(
        page => {
          this.errorMessage = '';
          this.tournamentsPage = page;
        },
        error => {
          this.errorMessage = 'Nie udało się załadować turniejów. Spróbuj jeszcze raz. W razie dalszych problemów, skontaktuj się z administratorem.';
          this.tournamentsPage = EMPTY_PAGE;
          this.isLoading = false;
        },
        () => this.isLoading = false
      );
  }

  private loadFinishedTournaments(): void {
    this.tournamentPreviewService.getFinishedManageTournaments(this.currentPage, this.pageSize, this.query)
      .subscribe(
        page => {
          this.errorMessage = '';
          this.tournamentsPage = page;
        },
        error => {
          this.errorMessage = 'Nie udało się załadować turniejów. Spróbuj jeszcze raz. W razie dalszych problemów, skontaktuj się z administratorem.';
          this.tournamentsPage = EMPTY_PAGE;
          this.isLoading = false;
        },
        () => this.isLoading = false
      );
  }
}
