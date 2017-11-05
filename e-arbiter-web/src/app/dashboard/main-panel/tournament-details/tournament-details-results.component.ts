import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {TournamentDetails} from '../interface/tournament-details.interface';
import {ResultService} from '../service/result.service';
import {UserTournamentResults} from '../interface/user-tournament-results.interface';
import {AuthService} from '../../../shared/service/auth.service';
import {ModalService} from '../../../shared/service/modal.service';
import {TournamentManagementService} from '../../tournament-management-panel/tournament-management.service';
import {TournamentManageService} from '../service/tournament-manage.service';
import {User} from '../../../shared/interface/user.interface';
import {MainPanelStream} from '../service/main-panel.stream';
import {Subscription} from 'rxjs/Subscription';

declare var $: any;

@Component({
  selector: 'arb-tour-details-results',
  template: `
    <div class="tournament-details-card__results-panel" *ngIf="canSeeResults() && tournamentDetails?.users > 0">
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
            <i *ngIf="isUserTheOwner() && isActive()" (click)="removeUser(userResults.userName)"
               class="remove user icon"></i>
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
export class TournamentDetailsResultsComponent implements OnInit, OnDestroy {
  @Input() tournamentDetails: TournamentDetails;
  public results: UserTournamentResults[] = [];
  public errorMessage = '';

  private enrolledUsers: User[];
  private loadResults$: Subscription;

  constructor(private resultService: ResultService, private tournamentManageService: TournamentManageService,
              private authService: AuthService, private modalService: ModalService, private mainPanelStream: MainPanelStream) {
  }

  ngOnInit(): void {
    this.loadResults$ = this.mainPanelStream.getLoadCurrentTournamentResults()
      .subscribe(this.updateResults.bind(this));

    this.updateResults();
  }


  ngOnDestroy(): void {
    this.loadResults$.unsubscribe();
  }

  public canSeeResults(): boolean {
    if (!this.tournamentDetails) {
      return false;
    }

    const {resultsVisible, owner, participateInTournament} = this.tournamentDetails.accessDetails;
    return (owner || (participateInTournament && resultsVisible)) && this.tournamentDetails.status !== 'DRAFT';
  }

  public isUserTheOwner(): boolean {
    return this.authService.getLoggedInUserName() === this.tournamentDetails.ownerName;
  }

  isActive(): boolean {
    return this.tournamentDetails.status === 'ACTIVE';
  }

  public removeUser(username: string): void {
    const userToRemove = this.enrolledUsers.find(user => user.name === username);
    this.modalService.askQuestion(`Czy na pewno usunąć z turnieju użytkownika ${userToRemove.name}?`, () => {
      this.tournamentManageService.removeUserFromTournament(this.tournamentDetails.id, userToRemove.id)
        .first()
        .subscribe(
          data => {
            this.mainPanelStream.callUpdateCurrentTournamentDetails();
            this.mainPanelStream.callLoadCurrentTournamentResults();
          },
          error => {
            this.modalService.showAlert('Nie można usunąć użytkownika.')
          });
    });
  }

  private updateResults() {
    if (this.canSeeResults()) {
      this.loadResults();
      this.loadUsers();
    }
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
          this.enrolledUsers = [];
        });
  }

  private loadUsers() {
    if (this.isUserTheOwner()) {
      this.tournamentManageService.getEnrolledUsers(this.tournamentDetails.id)
        .first()
        .subscribe(
          users => {
            this.errorMessage = '';
            this.enrolledUsers = users;
          },
          error => {
            this.errorMessage = 'Nie udało się wczytać wyników';
            this.results = [];
            this.enrolledUsers = [];
          });
    }
  }
}
