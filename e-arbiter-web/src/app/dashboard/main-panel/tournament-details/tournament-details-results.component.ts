import {Component, Input, OnInit} from '@angular/core';
import {TournamentDetails} from '../interface/tournament-details.interface';
import {ResultService} from '../service/result.service';
import {UserTournamentResults} from '../interface/user-tournament-results.interface';
import {AuthService} from '../../../shared/service/auth.service';

declare var $: any;

@Component({
  selector: 'arb-tour-details-results',
  template: `
    <div class="tournament-details-card__results-panel" *ngIf="canSeeResults()">
      <div class="tournament-details-card__subtitle">
        <h4><i class="table icon"></i>Wyniki</h4>
      </div>
      <table class="ui compact celled definition small table" *ngIf="results && results.length > 0">
        <thead>
        <tr>
          <th></th>
          <th>Użytkownik</th>
          <th *ngFor="let taskResult of results[0].taskResults; let i = index">#{{i + 1}}</th>
          <th>W sumie</th>
        </tr>
        </thead>
        <tbody>
        <tr
          *ngFor="let userResults of results"
          [ngClass]="(authService.getLoggedInUserName() === userResults.userName) ? 'active-user' : ''"
        >
          <td class="collapsing">
            #{{userResults.position}}
          </td>
          <td>
            <img class="ui avatar image" src="{{authService.getUserImgLinkByName(userResults.userName)}}">
            <a class="table-link" target="_blank" href="https://github.com/{{userResults.userName}}">
              {{userResults.userName}}
            </a>
          </td>
          <td *ngFor="let taskResult of userResults.taskResults"
          [ngClass]="(taskResult.earnedPoints == 0) ? 'negative' : ''">
            {{taskResult.earnedPoints}}
          </td>
          <td>
            <b>{{userResults.summaryPoints}}/{{tournamentDetails.maxPoints}}</b>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="ui red message" *ngIf="errorMessage.length > 0">
        {{errorMessage}}
      </div>
    </div>
  `
})
export class TournamentDetailsResultsComponent implements OnInit {
  @Input() tournamentDetails: TournamentDetails;
  public results: UserTournamentResults[] = [];
  public errorMessage = '';

  constructor(private resultService: ResultService, private authService: AuthService) {
  }

  ngOnInit(): void {
    if (this.canSeeResults()) {
      this.loadResults();
    }
  }

  public canSeeResults(): boolean {
    if (!this.tournamentDetails) {
      return false;
    }

    const {resultsVisible, owner, participateInTournament} = this.tournamentDetails.accessDetails;
    return (owner || (participateInTournament && resultsVisible)) && this.tournamentDetails.status !== 'DRAFT';
  }

  private loadResults() {
    this.resultService.getResults(this.tournamentDetails.id)
      .first()
      .subscribe(
        results => {
          this.errorMessage = '';
          this.results = results;
        },
        error => {
          this.errorMessage = 'Nie udało się wczytać wyników';
          this.results = [];
        });
  }
}
