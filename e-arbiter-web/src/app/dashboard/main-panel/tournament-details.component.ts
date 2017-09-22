import {Component, OnDestroy, OnInit} from '@angular/core';
import {TournamentDetailsService} from '../../shared/service/tournament-details.service';
import {TournamentDetails} from '../../shared/interface/tournament-details.interface';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';
import {DateService} from '../../shared/service/date.service';
import * as _ from 'lodash';
import {TaskPreview} from '../../shared/interface/task-preview.interface';
import {TournamentStatus} from '../../shared/interface/tournament-status.enum';


@Component({
  selector: 'arb-tour-details',
  template: `
    <div class="ui container center aligned scrollable-page-view">
      <div class="ui active centered loader massive" *ngIf="isLoading; else tournamentDetailsView"></div>
      <ng-template #tournamentDetailsView>
        <div class="tournament-card">
          <div class="tournament-card__title-container">
            <div class="tournament-card__title">
              <h4>{{tournamentDetails.name}}</h4>
            </div>
            <div class="tournament-card__subtitle">
              <p class="tournament-card__subtitle__text">Deadline: {{endDate}}</p>
              <p class="tournament-card__subtitle__text">{{accessibilityStatus}}</p>
            </div>
          </div>
          <div class="tournament-card__main-container--full">
            <p class="tournament-card__main-container__text">{{tournamentDetails?.description}}</p>
          </div>
        </div>
        <div class="tournament-details-card">
          <div class="tournament-details-card__tasks-panel">
            <div class="tournament-details-card__subtitle">
              <h4>Zadania</h4>
            </div>
            <div class="tournament-details-card__tasks-panel__tasks-list">
              <div class="tournament-details-card__tasks-panel__tasks-list__task"
                   *ngFor="let task of tournamentDetails?.taskPreviews; trackBy: trackByName">
                <sm-accordion>
                  <sm-accordion-item>
                    <accordion-title>
                      {{task.name}}
                    </accordion-title>
                    <accordion-content>
                      <p>{{task.description}}</p>
                      <div class="accordion-details-footer">
                        <p>Podejścia: {{task.userAttempts}}/{{convertAttempts(task.maxAttempts)}}</p>
                        <p>Punkty: {{task.pointsReceived}}/{{task.maxPoints}}</p>
                        <button
                          [ngClass]="!canExecuteTask(task) ? 'ui button medium teal disabled' : 'ui button medium teal'"
                          (click)="executeTask(task)"
                        >
                          Wykonaj zadanie
                        </button>
                      </div>
                    </accordion-content>
                  </sm-accordion-item>
                </sm-accordion>
              </div>
            </div>
          </div>
          <div class="tournament-details-card__stats-panel">
            <div class="tournament-details-card__subtitle">
              <h4>Statystyki</h4>
            </div>
            <div class="tournament-details-card__stats-panel__stat">
              <strong class="tournament-details-card__stats-panel__stat__label">Autor:</strong>
              <a target="_blank" href="https://github.com/{{tournamentDetails.ownerName}}"
                 class="tournament-details-card__stats-panel__stat__value">{{tournamentDetails.ownerName}}</a>
            </div>
            <div class="tournament-details-card__stats-panel__stat">
              <strong class="tournament-details-card__stats-panel__stat__label">Status:</strong>
              <p class="tournament-details-card__stats-panel__stat__value">{{tournamentDetails.status}}</p>
            </div>
            <div class="tournament-details-card__stats-panel__stat">
              <strong class="tournament-details-card__stats-panel__stat__label">Liczba użytkowników:</strong>
              <p class="tournament-details-card__stats-panel__stat__value">{{tournamentDetails?.users}}</p>
            </div>
            <div class="tournament-details-card__stats-panel__stat">
              <strong class="tournament-details-card__stats-panel__stat__label">Data rozpoczęcia:</strong>
              <p class="tournament-details-card__stats-panel__stat__value">{{startDate}}</p>
            </div>
            <div class="tournament-details-card__stats-panel__stat">
              <strong class="tournament-details-card__stats-panel__stat__label">Ilość zadań:</strong>
              <p class="tournament-details-card__stats-panel__stat__value">
                {{tournamentDetails?.taskPreviews.length}}</p>
            </div>
            <div class="tournament-details-card__stats-panel__stat">
              <strong class="tournament-details-card__stats-panel__stat__label">Max punktów:</strong>
              <p class="tournament-details-card__stats-panel__stat__value">{{tournamentDetails?.maxPoints}}</p>
            </div>
            <div class="tournament-details-card__stats-panel__stat">
              <strong class="tournament-details-card__stats-panel__stat__label">Twoje punkty:</strong>
              <p class="tournament-details-card__stats-panel__stat__value">{{tournamentDetails?.userPoints}}</p>
            </div>
          </div>
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
              private route: ActivatedRoute, private dateService: DateService) {

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

  public convertAttempts(attempts?: number): string {
    if (_.isNumber(attempts)) {
      return `${attempts}`
    } else {
      return 'nieograniczona'
    }
  }

  public canExecuteTask(task: TaskPreview): boolean {

    if (this.tournamentDetails.status === TournamentStatus.FINISHED) { // tasks cannot be executed if tournament is finished
      return false;
    }

    if (!_.isNumber(task.maxAttempts)) {  // no number, probably null -> inf amount of execution
      return true;
    }

    return task.userAttempts < task.maxAttempts;
  }

  public executeTask(task: TaskPreview) {
    console.log('Task to execute', task.name)
  }

  public trackByName(index: number, task: TaskPreview): string {
    return task.name;
  }
}
