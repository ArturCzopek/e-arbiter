import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class MainPanelStream {
  private updateCurrentTournamentDetails: Subject<any> = new Subject<any>();

  getUpdateCurrentTournamentDetails(): Subject<any> {
    return this.updateCurrentTournamentDetails;
  }

  public callUpdateCurrentTournamentDetails() {
    this.updateCurrentTournamentDetails.next(null);
  }
}
