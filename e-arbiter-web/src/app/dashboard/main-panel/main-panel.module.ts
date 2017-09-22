import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {MainPanelComponent} from 'app/dashboard/main-panel/main-panel.component';
import {CommonModule} from '@angular/common';
import {SharedModule} from '../../shared/shared.module';
import {TournamentDetailsComponent} from './tournament-details.component';
import {NgSemanticModule} from 'ng-semantic/ng-semantic';
import {TournamentDetailsStatisticsComponent} from './tournament-details-statistics.component';
import {TournamentDetailsTaskListComponent} from './tournament-details-task-list.component';
import {TournamentDetailsHeaderComponent} from './tournament-details-header.component';
import {TournamentDetailsTaskPreviewComponent} from "./tournament-details-task-preview.component";

@NgModule({
  declarations: [
    MainPanelComponent,
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
