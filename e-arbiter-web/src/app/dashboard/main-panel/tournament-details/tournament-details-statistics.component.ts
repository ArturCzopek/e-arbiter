import {Component, Input, OnInit} from '@angular/core';
import {TournamentDetails} from '../interface/tournament-details.interface';
import {RouteService} from "../../../shared/service/route.service";

@Component({
  selector: 'arb-tour-details-stats',
  template: `
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
        <p class="tournament-details-card__stats-panel__stat__value">{{status}}</p>
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
      <div *ngIf="canSeePoints()" class="tournament-details-card__stats-panel__stat">
        <strong class="tournament-details-card__stats-panel__stat__label">Twoje punkty:</strong>
        <p class="tournament-details-card__stats-panel__stat__value">{{tournamentDetails?.earnedPoints}}</p>
      </div>
      <div class="tournament-details-card__stats-panel__stat tournament-details-card__stats-panel__stat--single">
        <a *ngIf="canSeeResults()" (click)="goToResults()" class="tournament-details-card__stats-panel__stat__link">Zobacz
          wyniki</a>
      </div>
    </div>
  `
})
export class TournamentDetailsStatisticsComponent implements OnInit {
  @Input() tournamentDetails: TournamentDetails;
  @Input() startDate: String;
  public status = '';


  constructor(private routeService: RouteService) {
  }

  ngOnInit(): void {
    this.convertStatus();
  }


  public canSeePoints(): boolean {
    return this.tournamentDetails.accessDetails.participateInTournament;
  }

  public canSeeResults(): boolean {
    const {resultsVisible, owner, participateInTournament} = this.tournamentDetails.accessDetails;
    return (owner || participateInTournament) && resultsVisible;
  }

  public convertStatus(): void {
    if (this.tournamentDetails.status === 'DRAFT') {
      this.status = 'Szkic';
    } else if (this.tournamentDetails.status === 'ACTIVE') {
      this.status = 'Aktywny';
    } else if (this.tournamentDetails.status === 'FINISHED') {
      this.status = 'Zakończony';
    }
  }

  public goToResults(): void {
    this.routeService.goToTournamentResults(this.tournamentDetails.id);
  }
}
