import {Component, Input, ViewChild} from '@angular/core';
import {TournamentDetails} from '../interface/tournament-details.interface';
import {AuthService} from '../../../shared/service/auth.service';
import {TournamentManageService} from '../service/tournament-manage.service';
import {ModalService} from '../../../shared/service/modal.service';
import {RouteService} from '../../../shared/service/route.service';
import {TournamentDetailsDeleteModalComponent} from 'app/dashboard/main-panel/tournament-details/tournament-details-delete.modal.component';

@Component({
  selector: 'arb-tour-details-manage',
  template: `
    <div *ngIf="isUserTheOwner() && isDraft()" class="ui center aligned segment">
      <button (click)="editTournament()" class="ui large blue button">
        <i class="edit icon"></i>
        Edytuj
      </button>
      <button (click)="activateTournament()" class="ui large teal button">
        <i class="hand peace icon"></i>
        Aktywuj
      </button>
      <button (click)="showDeleteModal()" class="ui large red button">
        <i class="remove icon"></i>
        Usuń
      </button>
    </div>
    <div *ngIf="isUserTheOwner() && isActive()" class="ui center aligned segment">
      <div class="ui action input">
        <input [(ngModel)]="extendValue" type="number" min="0">
        <select [(ngModel)]="extendUnit" class="ui compact selection dropdown">
          <option selected="" value="d">Dni</option>
          <option value="h">Godzin</option>
        </select>
        <div (click)="extendDeadline()" class="ui teal large button">Przedłuż</div>
      </div>
    </div>

    <arb-tour-details-delete-modal
      #deleteTournamentModal
      [tournamentDetails]="tournamentDetails"
    ></arb-tour-details-delete-modal>
  `
})
export class TournamentDetailsManageComponent {

  @Input() tournamentDetails: TournamentDetails;
  @ViewChild('deleteTournamentModal') deleteTournamentModal: TournamentDetailsDeleteModalComponent;

  extendValue = 0;
  extendUnit = 'd';

  constructor(private authService: AuthService, private modalService: ModalService,
              private tournamentManageService: TournamentManageService, private routeService: RouteService) {
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
