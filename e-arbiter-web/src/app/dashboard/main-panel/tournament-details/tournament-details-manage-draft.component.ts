import {Component, Input, ViewChild} from '@angular/core';
import {RouteService} from '../../../shared/service/route.service';
import {ModalService} from '../../../shared/service/modal.service';
import {TournamentDetails} from '../interface/tournament-details.interface';
import {TournamentManageService} from '../service/tournament-manage.service';
import {TournamentDetailsDeleteModalComponent} from './tournament-details-delete.modal.component';

@Component({
  selector: 'arb-tour-details-manage-draft',
  template: `
    <div class="ui center aligned segment">
      <div class="tournament-details-card__subtitle">
        <h4><i class="student icon"></i>Zarządzaj</h4>
      </div>
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

    <arb-tour-details-delete-modal
      #deleteTournamentModal
      [tournamentDetails]="tournamentDetails"
    ></arb-tour-details-delete-modal>
  `
})
export class TournamentDetailsManageDraftComponent {

  @Input() tournamentDetails: TournamentDetails;
  @ViewChild('deleteTournamentModal') deleteTournamentModal: TournamentDetailsDeleteModalComponent;

  constructor(private routeService: RouteService, private modalService: ModalService,
              private tournamentManageService: TournamentManageService) {
  }

  editTournament(): void {
    this.routeService.goToTournamentEditForm(this.tournamentDetails.id);
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

  showDeleteModal(): void {
    this.deleteTournamentModal.showModal();
  }

}
