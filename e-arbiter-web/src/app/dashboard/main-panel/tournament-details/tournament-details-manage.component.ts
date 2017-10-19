import {Component, Input} from "@angular/core";
import {TournamentDetails} from "../interface/tournament-details.interface";
import {AuthService} from "../../../shared/service/auth.service";
import {TournamentManageService} from "../service/tournament-manage.service";
import {ModalService} from "../../../shared/service/modal.service";

@Component({
  selector: 'arb-tour-details-manage',
  template: `
    <div *ngIf="isUserTheOwner()" class="ui center aligned segment">
      <button class="ui teal button">Edytuj</button>
      <button *ngIf="isDraft()" (click)="activateTournament()" class="ui red button">Aktywuj</button>
    </div>
  `
})
export class TournamentDetailsManageComponent {

  @Input() tournamentDetails: TournamentDetails;

  constructor(private authService: AuthService, private modalService: ModalService,
              private tournamentManageService: TournamentManageService) {}

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
        data => this.tournamentDetails.status = 'ACTIVE',
        error => this.modalService.showAlert('Nie można aktywować turnieju.')
      );
  }
}
