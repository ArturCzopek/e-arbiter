import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {MainPanelComponent} from 'app/dashboard/main-panel/main-panel.component';
import {CommonModule} from '@angular/common';
import {SharedModule} from '../../shared/shared.module';
import {TournamentDetailsComponent} from './tournament-details/tournament-details.component';
import {NgSemanticModule} from 'ng-semantic/ng-semantic';
import {TournamentDetailsStatisticsComponent} from './tournament-details/tournament-details-statistics.component';
import {TournamentDetailsTaskListComponent} from './tournament-details/tournament-details-task-list.component';
import {TournamentDetailsHeaderComponent} from './tournament-details/tournament-details-header.component';
import {TournamentDetailsTaskPreviewComponent} from './tournament-details/tournament-details-task-preview.component';
import {TournamentDetailsActionComponent} from './tournament-details/tournament-details-action.component';

@NgModule({
  declarations: [
    MainPanelComponent,
    TournamentDetailsActionComponent,
    TournamentDetailsComponent,
    TournamentDetailsHeaderComponent,
    TournamentDetailsStatisticsComponent,
    TournamentDetailsTaskListComponent,
    TournamentDetailsTaskPreviewComponent
  ],
  imports: [
    CommonModule,
    NgSemanticModule,
    SharedModule
  ],
  exports: [
    MainPanelComponent,
    TournamentDetailsComponent
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class MainPanelModule {

}
