import {Component, Input} from '@angular/core';
import {TournamentDetails} from '../interface/tournament-details.interface';
import {ModalService} from '../../../shared/service/modal.service';
import {RouteService} from '../../../shared/service/route.service';
import {TournamentManageService} from '../service/tournament-manage.service';

@Component({
  selector: 'arb-tour-details-manage-active',
  template: `
    <div class="ui center aligned segment">
      <div class="tournament-details-card__subtitle">
        <h4><i class="add to calendar icon"></i>Przedłuż deadline</h4>
      </div>
      <div class="ui action input">
        <input [(ngModel)]="extendValue" type="number" min="0">
        <select [(ngModel)]="extendUnit" class="ui compact selection dropdown">
          <option selected="" value="d">Dni</option>
          <option value="h">Godzin</option>
        </select>
        <div (click)="extendDeadline()" class="ui teal large button">Przedłuż</div>
      </div>
    </div>
  `
})
export class TournamentDetailsManageActiveComponent {

  @Input() tournamentDetails: TournamentDetails;

  extendValue = 0;
  extendUnit = 'd';

  constructor(private modalService: ModalService, private routeService: RouteService,
              private tournamentManageService: TournamentManageService) {
  }

  extendDeadline(): void {
    const multiplier = this.extendUnit === 'd' ? TimeMultiplier.SEC_IN_DAY : TimeMultiplier.SEC_IN_HOUR;

    this.tournamentManageService.extendDeadline(this.tournamentDetails.id, Math.ceil(this.extendValue * multiplier))
      .first()
      .subscribe(
        data => {
          this.modalService.showAlert('Deadline zaktualizowany.', () => this.routeService.goToTournamentManagement());
        },
        error => this.modalService.showAlert('Nie można zaktualizować deadline\'u.')
      );
  }
}

const TimeMultiplier = {
  SEC_IN_DAY: 24 * 60 * 60,
  SEC_IN_HOUR: 60 * 60
};
