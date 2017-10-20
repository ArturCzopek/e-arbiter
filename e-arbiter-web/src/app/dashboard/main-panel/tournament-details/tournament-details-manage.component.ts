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
      <button (click)="editTournament()" class="ui blue button">Edytuj</button>
      <button (click)="activateTournament()" class="ui teal button">Aktywuj</button>
      <button (click)="showDeleteModal()" class="ui red button">Usuń</button>
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

  constructor(private authService: AuthService, private modalService: ModalService,
              private tournamentManageService: TournamentManageService, private routeService: RouteService) {
  }

  isDraft(): boolean {
    return this.tournamentDetails.status === 'DRAFT';
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

  editTournament(): void {
    this.routeService.goToTournamentEditForm(this.tournamentDetails.id);
  }

  showDeleteModal(): void {
    this.deleteTournamentModal.showModal();
  }
}
