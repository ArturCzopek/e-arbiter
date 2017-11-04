import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class MainPanelStream {
  private updateCurrentTournamentDetails: Subject<any> = new Subject<any>();
  private loadCurrentTournamentResults: Subject<any> = new Subject<any>();

  getUpdateCurrentTournamentDetails(): Subject<any> {
    return this.updateCurrentTournamentDetails;
  }

  getLoadCurrentTournamentResults(): Subject<any> {
    return this.loadCurrentTournamentResults;
  }

  public callUpdateCurrentTournamentDetails() {
    this.updateCurrentTournamentDetails.next(null);
  }

  public callLoadCurrentTournamentResults() {
    setTimeout(() => this.loadCurrentTournamentResults.next(null), 300);  // time for reload some inputs etc is needed there
  }
}
