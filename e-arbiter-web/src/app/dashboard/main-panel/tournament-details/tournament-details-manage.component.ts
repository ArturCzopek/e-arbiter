import {Component, Input} from '@angular/core';
import {TournamentDetails} from '../interface/tournament-details.interface';
import {AuthService} from '../../../shared/service/auth.service';

@Component({
  selector: 'arb-tour-details-manage',
  template: `
    <arb-tour-details-manage-draft
      *ngIf="isUserTheOwner() && isDraft()"
      [tournamentDetails]="tournamentDetails"
    ></arb-tour-details-manage-draft>
    <arb-tour-details-manage-active
      *ngIf="isUserTheOwner() && isActive()"
      [tournamentDetails]="tournamentDetails"
    ></arb-tour-details-manage-active>
    <arb-tour-details-manage-finished
      *ngIf="isUserTheOwner() && isFinished()"
      [tournamentDetails]="tournamentDetails"
    ></arb-tour-details-manage-finished>
  `
})
export class TournamentDetailsManageComponent {

  @Input() tournamentDetails: TournamentDetails;

  constructor(private authService: AuthService) {
  }

  isDraft(): boolean {
    return this.tournamentDetails.status === 'DRAFT';
  }

  isActive(): boolean {
    return this.tournamentDetails.status === 'ACTIVE';
  }

  isFinished(): boolean {
    return this.tournamentDetails.status === 'FINISHED';
  }

  isUserTheOwner(): boolean {
    return this.authService.getLoggedInUserName() === this.tournamentDetails.ownerName;
  }
}
