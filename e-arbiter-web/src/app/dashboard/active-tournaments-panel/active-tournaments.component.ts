import {Component, OnInit} from '@angular/core';
import {TournamentPreviewService} from '../../shared/service/tournament-preview.service';
import {TournamentPreview} from '../../shared/interface/tournament-preview.interface';
import {EMPTY_PAGE, Page} from '../../shared/interface/page.interface';
import {MenuElement} from '../../shared/model/menu-element.model';
import {ActiveTournamentSort} from './model/active-tournament-sort.enum';

@Component({
  selector: 'arb-active-panel',
  template: `
    <div class="ui container center aligned scrollable-page-view">
      <arb-menu
        [menuElements]="menuElements"
        [querySearchEnabled]="false"
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
        <arb-pagination [page]="tournamentsPage" (onPageChange)="changePage($event)"></arb-pagination>
      </ng-template>
    </div>`
})
export class ActiveTournamentsComponent implements OnInit {

  public currentPage = 1;
  public pageSize = 5;
  public tournamentsSort = ActiveTournamentSort.NEWEST;
  public tournamentsPage: Page<TournamentPreview> = EMPTY_PAGE;
  public isLoading = true;
  public errorMessage = '';

  public menuElements = [
    new MenuElement('Najnowsze', ActiveTournamentSort.NEWEST),
    new MenuElement('Najpopularniejsze', ActiveTournamentSort.MOST_POPULAR),
    new MenuElement('Bliskie końca', ActiveTournamentSort.ALMOST_ENDED),
  ]

  constructor(private tournamentPreviewService: TournamentPreviewService) {
  }

  ngOnInit(): void {
    this.loadProperTournaments();
  }

  public changeStatus(tournamentsSort: ActiveTournamentSort) {
    this.tournamentsSort = tournamentsSort;
    this.currentPage = 1;
    this.loadProperTournaments();
  }

  public changePage(pageNumber: number) {
    this.currentPage = pageNumber;
    this.loadProperTournaments();
  }

  public findTournaments(queryData: any) {
    const {pageSize, query} = queryData;

    if (pageSize !== this.pageSize) {
      this.currentPage = 1;
    }

    this.pageSize = pageSize;
    this.loadProperTournaments();
  }

  public trackById(index: number, tournament: TournamentPreview): string {
    return tournament.id;
  }

  private loadProperTournaments() {
    if (this.tournamentsSort === ActiveTournamentSort.NEWEST) {
      this.loadNewestTournaments()
    } else if (this.tournamentsSort === ActiveTournamentSort.MOST_POPULAR) {
      this.loadMostPopularTournaments();
    } else {
      this.loadAlmostEndedTournaments();
    }
  }

  private loadNewestTournaments(): void {
    this.isLoading = true;

    this.tournamentPreviewService.getNewestPublicTournaments(this.currentPage, this.pageSize)
      .subscribe(
        page => {
          this.errorMessage = '';
          this.tournamentsPage = page;
        },
        error => {
          this.errorMessage = 'Nie udało się załadować turniejów. Spróbuj jeszcze raz. W razie dalszych problemów, skontaktuj się z administratorem.';
          this.tournamentsPage = EMPTY_PAGE;
        },
        () => this.isLoading = false);
  }

  private loadMostPopularTournaments(): void {
    this.tournamentPreviewService.getMostPopularPublicTournaments(this.currentPage, this.pageSize)
      .subscribe(
        page => {
          this.errorMessage = '';
          this.tournamentsPage = page;
        },
        error => {
          this.errorMessage = 'Nie udało się załadować turniejów. Spróbuj jeszcze raz. W razie dalszych problemów, skontaktuj się z administratorem.';
          this.tournamentsPage = EMPTY_PAGE;
        },
        () => this.isLoading = false
      );
  }

  private loadAlmostEndedTournaments() {
    this.tournamentPreviewService.getAlmostEndedPublicTournaments(this.currentPage, this.pageSize)
      .subscribe(
        page => {
          this.errorMessage = '';
          this.tournamentsPage = page;
        },
        error => {
          this.errorMessage = 'Nie udało się załadować turniejów. Spróbuj jeszcze raz. W razie dalszych problemów, skontaktuj się z administratorem.';
          this.tournamentsPage = EMPTY_PAGE;
        },
        () => this.isLoading = false
      );
  }
}
