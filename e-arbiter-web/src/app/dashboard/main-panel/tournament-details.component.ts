import {Component, OnDestroy, OnInit} from '@angular/core';
import {TournamentDetailsService} from '../../shared/service/tournament-details.service';
import {TournamentDetails} from './interface/tournament-details.interface';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';
import {DateService} from '../../shared/service/date.service';


@Component({
  selector: 'arb-tour-details',
  template: `
    <div class="ui container center aligned scrollable-page-view">
      <div class="ui active centered loader massive" *ngIf="isLoading; else tournamentDetailsView"></div>
      <ng-template #tournamentDetailsView>
        <arb-tour-details-header
          [tournamentDetails]="tournamentDetails"
          [accessibilityStatus]="accessibilityStatus"
          [endDate]="endDate"
        ></arb-tour-details-header>
        <div class="tournament-details-card">
          <arb-tour-details-task-list
            [taskPreviews]="tournamentDetails?.taskPreviews"
          ></arb-tour-details-task-list>
          <arb-tour-details-stats
            [tournamentDetails]="tournamentDetails"
            [startDate]="startDate"
          ></arb-tour-details-stats>
        </div>
      </ng-template>
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

  constructor(private tournamentDetailsService: TournamentDetailsService,
              private route: ActivatedRoute,
              private dateService: DateService) {

  }

  ngOnInit(): void {
    this.params$ = this.route.params.subscribe(params => {
      this.isLoading = true;
      const id = params['id'];
      this.tournamentDetailsService.getDetailsForTournament(id)
        .subscribe(
          details => {
            this.tournamentDetails = details;
            this.endDate = this.dateService.parseLocalDateTimeToString(this.tournamentDetails.endDate);
            this.startDate = this.dateService.parseLocalDateTimeToString(this.tournamentDetails.startDate);
            this.accessibilityStatus = (this.tournamentDetails.accessDetails.publicFlag) ? 'Turniej publiczny' : 'Turniej prywatny';
          },
          error => console.error(error),
          () => this.isLoading = false
        );
    });
  }

  ngOnDestroy(): void {
    this.params$.unsubscribe();
  }
}
