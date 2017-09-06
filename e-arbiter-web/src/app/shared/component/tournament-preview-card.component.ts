
import {Component, Input} from "@angular/core";
import {TournamentPreview} from "../interface/tournament-preview.interface";

@Component({
  selector: 'arb-tour-prev-card',
  template: `
    <div class="tournament-card">
        <div class="tournament-card__title-container">
            <div class="tournament-card__title">
                <h4>{{tournamentPreview.name}}</h4>
            </div>
            <div class="tournament-card__subtitle">
                <p>{{tournamentPreview.endDate}}</p>
                <p>{{tournamentPreview.status}}</p>
            </div>
        </div>
        <div class="tournament-card__main-container">
            <p>{{tournamentPreview.description}}</p>
        </div>
        <div class="tournament-card__footer">
            <div class="tournament-card__footer__info">
                <p>Liczba uczestników: {{tournamentPreview.users}}</p>
                <p>Autor: {{tournamentPreview.ownerName}}</p>
            </div>
            <button class="ui teal button" type="button" (click)="goToDetails()">Przejdź do turnieju</button>
        </div>
    </div>
  `
})
export class TournamentPreviewCardComponent {
  @Input() tournamentPreview: TournamentPreview;

  public goToDetails() {
    console.log("Go to ", this.tournamentPreview.id);
  }
}
