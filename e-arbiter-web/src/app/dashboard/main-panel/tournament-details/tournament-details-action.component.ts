import {AfterViewInit, ChangeDetectorRef, Component, Input} from '@angular/core';
import {TournamentDetails} from '../interface/tournament-details.interface';
import {TournamentStatus} from '../../../shared/interface/tournament-status.enum';

@Component({
  selector: 'arb-tour-details-action',
  template: `
    <div [ngClass]="(canJoinToTournament() || canLeaveTournament()) ? 'tournament-details-action-panel' : 'tournament-details-action-panel--empty'">
      <form *ngIf="!tournamentDetails.accessDetails.publicFlag && canJoinToTournament()" class="ui form">
        <div class="pull-center inline fields">
          <div class="field">
            <label>Hasło</label>
            <input [type]="showPassword ? 'text' : 'password'"/>
          </div>
          <div class="ui checkbox">
            <input type="checkbox" [checked]="showPassword" (click)="togglePasswordVisibility()">
            <label>Pokaż</label>
          </div>
        </div>
      </form>
      <button *ngIf="canJoinToTournament()" class="ui huge teal button" (click)="onJoinTournament()">
        <i class="add user icon"></i>
        Dołącz do turnieju
      </button>
      <button *ngIf="canLeaveTournament()" class="ui huge red button" (click)="onLeaveTournament()">
        <i class="remove user icon"></i>
        Opuść turniej
      </button>
    </div>
  `
})
export class TournamentDetailsActionComponent implements AfterViewInit {

  @Input() tournamentDetails: TournamentDetails;
  public showPassword = false;

  constructor(private cdr: ChangeDetectorRef) {
  }

  ngAfterViewInit(): void {
    setTimeout(() => this.cdr.detach(), 500);
  }

  public canJoinToTournament(): boolean {
    const {owner, participateInTournament} = this.tournamentDetails.accessDetails;

    if (!owner && !participateInTournament && this.tournamentDetails.status === 'ACTIVE') {
      return true;
    }

    return false;
  }

  public canLeaveTournament(): boolean {
    const {owner, participateInTournament} = this.tournamentDetails.accessDetails;

    if (!owner && participateInTournament && this.tournamentDetails.status === 'ACTIVE') {
      return true;
    }

    return false;
  }

  public onJoinTournament(): void {
    // TODO: implement joining
    console.log('Join');
  }

  public onLeaveTournament(): void {
    // TODO: implement leaving
    console.log('Leave');
  }


  public togglePasswordVisibility() {
    this.cdr.reattach();
    this.showPassword = !this.showPassword;
    this.cdr.detectChanges();
    setTimeout(() => this.cdr.detach(), 500);
  }
}
