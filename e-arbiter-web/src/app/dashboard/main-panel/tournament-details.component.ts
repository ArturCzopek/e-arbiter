import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {TournamentDetailsService} from '../../shared/service/tournament-details.service';
import {TournamentDetails} from './interface/tournament-details.interface';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';
import {DateService} from '../../shared/service/date.service';

@Component({
  selector: 'arb-tour-details',
  template: `
    <div class="ui container center aligned scrollable-page-view">
      <div class="ui active centered text loader massive" *ngIf="isLoading">
        Ładowanie...
      </div>
      <div class="ui red message" *ngIf="!isLoading && errorMessage.length > 0">
        {{errorMessage}}
      </div>
      <div *ngIf="!isLoading && !errorMessage">
        <arb-tour-details-header
          [tournamentDetails]="tournamentDetails"
          [accessibilityStatus]="accessibilityStatus"
          [endDate]="endDate"
          [canSeeHeaderData]="canSeeHeaderData()"
        ></arb-tour-details-header>
        <arb-tour-details-action
          [tournamentDetails]="tournamentDetails"
        ></arb-tour-details-action>
        <div class="tournament-details-card" *ngIf="canSeeTournamentMainDetails()">
          <arb-tour-details-task-list
            [taskPreviews]="tournamentDetails?.taskPreviews"
            [status]="tournamentDetails.status"
            [canSeeTaskFooter]="canSeeTaskFooter()"
          ></arb-tour-details-task-list>
          <arb-tour-details-stats
            [tournamentDetails]="tournamentDetails"
            [startDate]="startDate"
          ></arb-tour-details-stats>
        </div>
      </div>
    </div>
  `
})
export class TournamentDetailsComponent implements OnInit, OnDestroy {

  public tournamentDetails: TournamentDetails;
  public isLoading = true;
  public endDate: string;
  public startDate: string;
  public accessibilityStatus: string;
  public params$: Subscription;
  public errorMessage = '';

  constructor(private tournamentDetailsService: TournamentDetailsService,
              private route: ActivatedRoute,
              private dateService: DateService,
              private cdr: ChangeDetectorRef) {

  }

  ngOnInit(): void {
    this.params$ = this.route.params.first().subscribe(params => {
      this.isLoading = true;
      const id = params['id'];
      this.tournamentDetailsService.getDetailsForTournament(id)
        .subscribe(
          details => {
            this.tournamentDetails = details;
            this.endDate = (this.canSeeHeaderData()) ? this.dateService.parseLocalDateTimeToString(this.tournamentDetails.endDate) : '-';
            this.startDate = (this.canSeeHeaderData()) ? this.dateService.parseLocalDateTimeToString(this.tournamentDetails.startDate) : '-';
            this.accessibilityStatus = (this.tournamentDetails.accessDetails.publicFlag) ? 'Turniej publiczny' : 'Turniej prywatny';
            this.errorMessage = '';
          },
          error => this.errorMessage = 'Coś poszło nie tak! Prawdopodobnie turniej nie istnieje. ' +
            'Jeżeli masz wątpliwości, skontaktuj się z administratorem lub właścicielem turnieju',
          () => {
            this.isLoading = false
            setTimeout(() => this.cdr.detach(), 1000);  // give this feeling some time
          }
        );
    });
  }

  ngOnDestroy(): void {
    this.params$.unsubscribe();
  }

  public canSeeHeaderData(): boolean {
    const {publicFlag, owner, participateInTournament} = this.tournamentDetails.accessDetails;

    if (!publicFlag && !owner && !participateInTournament) {
      return false;
    }

    return true;
  }

  public canSeeTaskFooter(): boolean {
    return this.tournamentDetails.accessDetails.participateInTournament;
  }

  public canSeeTournamentMainDetails(): boolean {
    const {publicFlag, owner, participateInTournament} = this.tournamentDetails.accessDetails;

    if (!publicFlag && !owner && !participateInTournament) {
      return false;
    }

    return true;
  }
}
