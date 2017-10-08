import {Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {TournamentDetails} from '../interface/tournament-details.interface';
import {TournamentUserActionService} from '../service/tournament-user-action.service';
import {SemanticModalComponent} from 'ng-semantic';
import {TournamentUserActionRequest, TournamentUserActionType} from '../model/tournament-user-action.model';

@Component({
  selector: 'arb-tour-details-action',
  template: `
    <div
      [ngClass]="(canJoinToTournament() || canLeaveTournament()) ? 'tournament-details-action-panel' : 'tournament-details-action-panel--empty'">
      <form *ngIf="!tournamentDetails.accessDetails.publicFlag && canJoinToTournament()" class="ui form">
        <div class="pull-center inline fields">
          <div class="field">
            <label>Hasło</label>
            <input #passwordInput [type]="showPassword ? 'text' : 'password'"/>
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
      <div class="ui red message" *ngIf="errorMessage.length > 0">
        {{errorMessage}}
      </div>
      <sm-modal title="{{modalTitle}}" #actionInfoModal>
        <modal-content>
          {{modalMessage}}
        </modal-content>
        <modal-actions>
          <div class="ui buttons">
            <button class="ui teal button" (click)="onModalClick()">{{modalButtonLabel}}</button>
          </div>
        </modal-actions>
      </sm-modal>
    </div>
  `
})
export class TournamentDetailsActionComponent {

  @Input() tournamentDetails: TournamentDetails;
  @Output() onUserTournamentStatusChange = new EventEmitter<TournamentUserActionType>();
  @ViewChild('passwordInput') passwordInput: ElementRef;
  @ViewChild('actionInfoModal') actionInfoModal: SemanticModalComponent;

  public showPassword = false;
  public errorMessage = '';
  public modalMessage = '';
  public modalTitle = ''
  public modalButtonLabel = '';
  public lastActionType: TournamentUserActionType;

  constructor(private tournamentUserActionService: TournamentUserActionService) {
  }

  public onModalClick() {
    this.onUserTournamentStatusChange.emit(this.lastActionType);
    this.actionInfoModal.hide();
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
    const joinRequest = new TournamentUserActionRequest(
      TournamentUserActionType.JOIN,
      this.tournamentDetails.id
    );

    if (!this.tournamentDetails.accessDetails.publicFlag) {
      joinRequest.password = this.passwordInput.nativeElement.value;
    }

    this.lastActionType = TournamentUserActionType.JOIN;

    this.tournamentUserActionService.joinToTournament(joinRequest)
      .first()
      .subscribe(
        ok => {
          this.errorMessage = '';
          this.modalMessage = `Hura! Udało dołączyć się do turnieju ${this.tournamentDetails.name}!`;
          this.modalTitle = 'Dołączanie do turnieju';
          this.modalButtonLabel = 'Pokaż turniej';
          this.actionInfoModal.show({closable: false});
        },
        error => {
          this.errorMessage = `Coś poszło nie tak. ${(this.tournamentDetails.accessDetails.publicFlag) ? '' : 'Może podałeś złe hasło?'}
          Jeżeli nie jesteś pewien co może być przyczyną twoich problemów, skontaktuj się z autorem turnieju lub administratorem.`
          this.modalMessage = '';
          this.modalTitle = '';
          this.modalButtonLabel = '';
        }
      );
  }

  public onLeaveTournament(): void {
    const leaveRequest = new TournamentUserActionRequest(
      TournamentUserActionType.LEAVE,
      this.tournamentDetails.id
    );

    this.lastActionType = TournamentUserActionType.LEAVE;

    this.tournamentUserActionService.leaveTournament(leaveRequest)
      .first()
      .subscribe(
        ok => {
          this.errorMessage = '';
          this.modalMessage = `Udało się opuścić turniej ${this.tournamentDetails.name}`;
          this.modalTitle = 'Opuszczanie turnieju';
          this.modalButtonLabel = 'Przejdź do dashboardu';
          this.actionInfoModal.show({closable: false});
        },
        error => {
          this.errorMessage = `Coś poszło nie tak. Jeżeli nie jesteś pewien co może być przyczyną twoich problemów,
           skontaktuj się z autorem turnieju lub administratorem.`
          this.modalMessage = '';
          this.modalTitle = '';
          this.modalButtonLabel = '';
        }
      );
  }

  public togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }
}
