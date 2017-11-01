import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {TournamentDetails} from '../interface/tournament-details.interface';
import {AuthService} from '../../../shared/service/auth.service';
import {TournamentManageService} from '../service/tournament-manage.service';
import {ModalService} from '../../../shared/service/modal.service';
import {RouteService} from '../../../shared/service/route.service';
import {TournamentDetailsDeleteModalComponent} from 'app/dashboard/main-panel/tournament-details/tournament-details-delete.modal.component';
import {User} from "../../../shared/interface/user.interface";

@Component({
  selector: 'arb-tour-details-manage',
  template: `
    <div *ngIf="isUserTheOwner() && isDraft()" class="ui center aligned segment">
      <button (click)="editTournament()" class="ui blue button">Edytuj</button>
      <button (click)="activateTournament()" class="ui teal button">Aktywuj</button>
      <button (click)="showDeleteModal()" class="ui red button">Usuń</button>
    </div>
    <div *ngIf="isUserTheOwner() && isActive()" class="ui center aligned segment">
      <div>
        <h4 *ngIf="enrolledUsers && enrolledUsers.length > 0">Uczestnicy</h4>
        <div class="ui list">
          <div *ngFor="let user of enrolledUsers" class="item">
            <i (click)="removeUser(user)" class="remove user icon"></i>
            <div class="content">
              <a class="header">{{user.name}}</a>
              <div class="description">id: {{user.id}}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="ui action input">
        <input [(ngModel)]="extendValue" type="number" min="0">
        <select [(ngModel)]="extendUnit" class="ui compact selection dropdown">
          <option selected="" value="d">Dni</option>
          <option value="h">Godzin</option>
        </select>
        <div (click)="extendDeadline()" class="ui button">Przedłuż</div>
      </div>
    </div>

    <arb-tour-details-delete-modal
      #deleteTournamentModal
      [tournamentDetails]="tournamentDetails"
    ></arb-tour-details-delete-modal>
  `
})
export class TournamentDetailsManageComponent implements OnInit {

  @Input() tournamentDetails: TournamentDetails;
  @ViewChild('deleteTournamentModal') deleteTournamentModal: TournamentDetailsDeleteModalComponent;

  extendValue: number = 0;
  extendUnit: string = 'd';

  enrolledUsers: User[];

  constructor(private authService: AuthService, private modalService: ModalService,
              private tournamentManageService: TournamentManageService, private routeService: RouteService) {
  }

  ngOnInit() {
    if (this.isUserTheOwner() && this.isActive()) {
      this.tournamentManageService.getEnrolledUsers(this.tournamentDetails.id)
        .first()
        .subscribe(
          users => this.enrolledUsers = users
        )
    }
  }

  isDraft(): boolean {
    return this.tournamentDetails.status === 'DRAFT';
  }

  isActive(): boolean {
    return this.tournamentDetails.status === 'ACTIVE';
  }

  isUserTheOwner(): boolean {
    return this.authService.getLoggedInUserName() === this.tournamentDetails.ownerName;
  }

  activateTournament(): void {
    this.tournamentManageService.activateTournament(this.tournamentDetails.id)
      .first()
      .subscribe(
        data => {
          this.tournamentDetails.status = 'ACTIVE';
          this.modalService.showAlert('Turniej został aktywowany', () => this.routeService.goToTournamentManagement());
        },
        error => this.modalService.showAlert('Nie można aktywować turnieju.')
      );
  }

  extendDeadline(): void {
    const multiplier = this.extendUnit === 'd' ? TimeMultiplier.SEC_IN_DAY :
      TimeMultiplier.SEC_IN_HOUR;

    this.tournamentManageService.extendDeadline(this.tournamentDetails.id, Math.ceil(this.extendValue * multiplier))
      .first()
      .subscribe(
        data => {
          this.modalService.showAlert('Deadline zaktualizowany.', () => this.routeService.goToTournamentManagement());
        },
        error => this.modalService.showAlert('Nie można zaktualizować deadline-u.')
      );
  }

  removeUser(userToRemove: User): void {
    this.modalService.askQuestion(`Czy na pewno usunąć z turnieju użytkownika ${userToRemove.name + '(id: ' + userToRemove.id + ')'}?`, () => {
      this.tournamentManageService.removeUserFromTournament(this.tournamentDetails.id, userToRemove.id)
        .first()
        .subscribe(
          data => this.enrolledUsers = this.enrolledUsers.filter(user => user.id != userToRemove.id),
          error => this.modalService.showAlert('Nie można usunąć użytkownika.')
        );
    })
  }

  editTournament(): void {
    this.routeService.goToTournamentEditForm(this.tournamentDetails.id);
  }

  showDeleteModal(): void {
    this.deleteTournamentModal.showModal();
  }
}

const TimeMultiplier = {
  SEC_IN_DAY: 24 * 60 * 60,
  SEC_IN_HOUR: 60 * 60
};
