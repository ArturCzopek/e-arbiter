
import {AfterViewInit, ChangeDetectorRef, Component, Input, OnInit} from "@angular/core";
import {TournamentPreview} from "../interface/tournament-preview.interface";
import {DateService} from "../service/date.service";

@Component({
  selector: 'arb-tour-prev-card',
  template: `
    <div class="tournament-card">
        <div class="tournament-card__title-container">
            <div class="tournament-card__title">
                <h4>{{tournamentPreview.name}}</h4>
            </div>
            <div class="tournament-card__subtitle">
                <p class="tournament-card__subtitle__text">Deadline: {{endDate}}</p>
                <p class="tournament-card__subtitle__text">{{accessibilityStatus}}</p>
            </div>
        </div>
        <div class="tournament-card__main-container">
            <p class="tournament-card__main-container__text">{{tournamentPreview.description}}</p>
        </div>
        <div class="tournament-card__footer">
            <div class="tournament-card__footer__info">
                <p class="tournament-card__footer__info__text">Liczba uczestników: {{tournamentPreview.users}}</p>
                <p class="tournament-card__footer__info__text">Autor: <strong><a target="_blank" href="https://github.com/{{tournamentPreview.ownerName}}">{{tournamentPreview.ownerName}}</a></strong></p>
            </div>
            <button class="ui teal large button" type="button" (click)="goToDetails()">Przejdź do turnieju</button>
        </div>
    </div>
  `
})
export class TournamentPreviewCardComponent implements OnInit, AfterViewInit{
  @Input() tournamentPreview: TournamentPreview;

  public endDate: string;
  public accessibilityStatus: string;

  constructor(private dateService: DateService, private changeDetector: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.endDate = this.dateService.parseLocalDateTimeToString(this.tournamentPreview.endDate);
    this.accessibilityStatus = (this.tournamentPreview.publicFlag) ? "Turniej publiczny" : "Turniej prywatny";
  }

  ngAfterViewInit(): void {
    this.changeDetector.detach();
  }

  public goToDetails() {
    console.log("Go to", this.tournamentPreview.id);
  }
}
