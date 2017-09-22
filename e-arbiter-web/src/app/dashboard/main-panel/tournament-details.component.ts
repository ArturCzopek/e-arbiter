import {Component, OnDestroy, OnInit} from '@angular/core';
import {TournamentDetailsService} from '../../shared/service/tournament-details.service';
import {TournamentDetails} from '../../shared/interface/tournament-details.interface';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'arb-tour-details',
  template: `
    <div class="ui container center aligned scrollable-page-view">
      Witam Cię w moim templejćie,
      <p *ngIf="tournamentDetails">{{tournamentDetails | json}}</p>
    </div>
  `
})
export class TournamentDetailsComponent implements OnInit, OnDestroy {

  public tournamentDetails: TournamentDetails;
  public params$: Subscription;

  constructor(private tournamentDetailsService: TournamentDetailsService, private route: ActivatedRoute) {

  }


  ngOnInit(): void {
    this.params$ = this.route.params.subscribe(params => {
      const id = params['id'];
      this.tournamentDetailsService.getDetailsForTournament(id)
        .subscribe(
          details => this.tournamentDetails = details
        );
    });
  }

  ngOnDestroy(): void {
    this.params$.unsubscribe();
  }
}
