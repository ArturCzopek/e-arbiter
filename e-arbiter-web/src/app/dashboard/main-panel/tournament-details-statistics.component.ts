import {Component, Input} from '@angular/core';
import {TournamentDetails} from './interface/tournament-details.interface';

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
  `
})
export class TournamentDetailsStatisticsComponent {
  @Input() tournamentDetails: TournamentDetails;
  @Input() startDate: String;
}
