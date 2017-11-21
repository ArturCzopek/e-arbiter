import {Component, Input, ViewChild} from '@angular/core';
import {SemanticModalComponent} from 'ng-semantic';
import {TournamentManageService} from '../service/tournament-manage.service';
import {ModalService} from '../../../shared/service/modal.service';
import {RouteService} from '../../../shared/service/route.service';
import {TournamentDetails} from '../interface/tournament-details.interface';

@Component({
  selector: 'arb-tour-details-delete-modal',
  template: `
    <sm-modal title="Usuń turniej" #deleteTournamentModal>
      <modal-content *ngIf="tournamentDetails">
        <p>Jesteś pewien, że chcesz usunąć ten turniej? Dane nie będą możliwe do odzyskania!</p>
        <p>Aby potwierdzić, wpisz nazwę turnieju, a następnie naciśnij "Usuń"</p>
        <div class="ui input">
          <input type="text" placeholder="Nazwa turnieju..." [(ngModel)]="deleteInputModalValue"/>
        </div>
      </modal-content>
      <modal-actions>
        <div class="ui buttons">
          <button class="ui cancel button">Odrzuć</button>
          <button type="submit" class="ui ok red button" [disabled]="deleteInputModalValue !== tournamentDetails.name">
            Usuń
          </button>
        </div>
      </modal-actions>
    </sm-modal>
  `
})
export class TournamentDetailsDeleteModalComponent {

  @Input() tournamentDetails: TournamentDetails;
  @ViewChild('deleteTournamentModal') deleteTournamentModal: SemanticModalComponent;
  public deleteInputModalValue = '';

  constructor(private tournamentManageService: TournamentManageService,
              private modalService: ModalService,
              private routeService: RouteService) {

  }

  showModal(): void {
    this.deleteTournamentModal.show({
      closable: false,
      observeChanges: true,
      onDeny: () => this.deleteInputModalValue = '',
      onApprove: () => {
        this.deleteTournament()
        this.deleteInputModalValue = '';
      }
    })
  }


  private deleteTournament(): void {
    this.tournamentManageService.deleteTournament(this.tournamentDetails.id)
      .first()
      .subscribe(
        data => this.modalService.showAlert('Turniej został usunięty', () => this.routeService.goToTournamentManagement()),
        error => this.modalService.showAlert('Nie można usunąć turnieju.')
      );
  }
}
