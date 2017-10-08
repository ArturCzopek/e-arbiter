import {AfterViewInit, ChangeDetectorRef, Component, Input} from '@angular/core';
import {TournamentDetails} from '../interface/tournament-details.interface';

@Component({
  selector: 'arb-tour-details-header',
  template: `
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
        <p class="tournament-card__main-container__text">
          {{(canSeeHeaderData) ? tournamentDetails?.description : 'Nie masz uprawnień, aby widzieć opis turnieju'}}</p>
      </div>
    </div>
  `
})
export class TournamentDetailsHeaderComponent {
  @Input() tournamentDetails: TournamentDetails;
  @Input() accessibilityStatus: boolean;
  @Input() endDate: string;
  @Input() canSeeHeaderData: boolean;
}
