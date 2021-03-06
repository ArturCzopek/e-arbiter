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
import {TournamentDetailsManageComponent} from './tournament-details/tournament-details-manage.component';
import {FormsModule} from '@angular/forms';
import {TournamentDetailsDeleteModalComponent} from './tournament-details/tournament-details-delete.modal.component';
import {TournamentDetailsQuizUploadComponent} from './tournament-details/tournament-details-quiz-upload.component';
import {TournamentDetailsResultsComponent} from './tournament-details/tournament-details-results.component';
import {TournamentDetailsManageActiveComponent} from './tournament-details/tournament-details-manage-active.component';
import {TournamentDetailsManageDraftComponent} from './tournament-details/tournament-details-manage-draft.component';
import {TournamentDetailsManageFinishedComponent} from './tournament-details/tournament-details-manage-finished.component';

@NgModule({
  declarations: [
    MainPanelComponent,
    TournamentDetailsActionComponent,
    TournamentDetailsComponent,
    TournamentDetailsDeleteModalComponent,
    TournamentDetailsHeaderComponent,
    TournamentDetailsManageActiveComponent,
    TournamentDetailsManageComponent,
    TournamentDetailsManageDraftComponent,
    TournamentDetailsManageFinishedComponent,
    TournamentDetailsQuizUploadComponent,
    TournamentDetailsResultsComponent,
    TournamentDetailsStatisticsComponent,
    TournamentDetailsTaskListComponent,
    TournamentDetailsTaskPreviewComponent
  ],
  imports: [
    CommonModule,
    NgSemanticModule,
    SharedModule,
    FormsModule
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
