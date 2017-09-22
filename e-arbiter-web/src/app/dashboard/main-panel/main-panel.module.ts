import {NgModule} from '@angular/core';
import {MainPanelComponent} from 'app/dashboard/main-panel/main-panel.component';
import {CommonModule} from '@angular/common';
import {SharedModule} from '../../shared/shared.module';
import {TournamentDetailsComponent} from "./tournament-details.component";

@NgModule({
  declarations: [
    MainPanelComponent,
    TournamentDetailsComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ],
  exports: [
    MainPanelComponent,
    TournamentDetailsComponent
  ]
})
export class MainPanelModule {

}
