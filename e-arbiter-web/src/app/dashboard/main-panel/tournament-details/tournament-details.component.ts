import {Component, OnDestroy, OnInit} from '@angular/core';
import {TournamentDetailsService} from '../../../shared/service/tournament-details.service';
import {TournamentDetails} from '../interface/tournament-details.interface';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';
import {DateService} from '../../../shared/service/date.service';
import {TournamentUserActionType} from '../model/tournament-user-action.model';
import {RouteService} from '../../../shared/service/route.service';
import {MainPanelStream} from '../service/main-panel.stream';

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
          (onUserTournamentStatusChange)="onUserTournamentStatusChange($event)"
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
          <arb-tour-details-results
            [tournamentDetails]="tournamentDetails"
          ></arb-tour-details-results>
        </div>
        <arb-tour-details-manage
          [tournamentDetails]="tournamentDetails"
        ></arb-tour-details-manage>
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
  private tournamentId = '';
  private updateTournamentDetails$: Subscription;

  constructor(private tournamentDetailsService: TournamentDetailsService,
              private route: ActivatedRoute,
              private routeService: RouteService,
              private dateService: DateService,
              private mainPanelStream: MainPanelStream) {
  }

  ngOnInit(): void {
    this.params$ = this.route.params.first().subscribe(params => {
      this.isLoading = true;
      this.tournamentId = params['id'];
      this.loadTournamentDetails();
    });

    this.updateTournamentDetails$ = this.mainPanelStream.getUpdateCurrentTournamentDetails()
      .subscribe(this.loadTournamentDetails.bind(this))
  }

  ngOnDestroy(): void {
    this.params$.unsubscribe();
    this.updateTournamentDetails$.unsubscribe();
  }

  public onUserTournamentStatusChange(tournamentUserActionType: TournamentUserActionType) {
    if (tournamentUserActionType === TournamentUserActionType.JOIN) {
      this.loadTournamentDetails();
      this.mainPanelStream.callLoadCurrentTournamentResults();
    } else {
      this.routeService.goToDashboard();
    }
  }

  public canSeeHeaderData(): boolean {
    if (!this.tournamentDetails) {
      return false;
    }

    const {publicFlag, owner, participateInTournament} = this.tournamentDetails.accessDetails;

    if (!publicFlag && !owner && !participateInTournament) {
      return false;
    }

    return true;
  }

  public canSeeTaskFooter(): boolean {
    if (!this.tournamentDetails) {
      return false;
    }

    return this.tournamentDetails.accessDetails.participateInTournament;
  }

  public canSeeTournamentMainDetails(): boolean {
    if (!this.tournamentDetails) {
      return false;
    }

    const {publicFlag, owner, participateInTournament} = this.tournamentDetails.accessDetails;

    if (!publicFlag && !owner && !participateInTournament) {
      return false;
    }

    return true;
  }

  private loadTournamentDetails() {
    this.tournamentDetailsService.getDetailsForTournament(this.tournamentId)
      .first()
      .subscribe(
        details => {
          this.tournamentDetails = details;
          this.endDate = (this.canSeeHeaderData() && this.tournamentDetails.endDate)
            ? this.dateService.parseLocalDateTimeToString(this.tournamentDetails.endDate) : '-';
          this.startDate = (this.canSeeHeaderData() && this.tournamentDetails.startDate)
            ? this.dateService.parseLocalDateTimeToString(this.tournamentDetails.startDate) : '-';
          this.accessibilityStatus = (this.tournamentDetails.accessDetails.publicFlag) ? 'Turniej publiczny' : 'Turniej prywatny';
          this.errorMessage = '';
          this.isLoading = false;
          this.tournamentDetails.taskPreviews.forEach(taskPreview => taskPreview.tournamentId = this.tournamentId);
        },
        error => {
          this.errorMessage = 'Coś poszło nie tak! Prawdopodobnie turniej nie istnieje. ' +
            'Jeżeli masz wątpliwości, skontaktuj się z administratorem lub właścicielem turnieju';
          this.isLoading = false;
        }
      )
    ;
  }
}
