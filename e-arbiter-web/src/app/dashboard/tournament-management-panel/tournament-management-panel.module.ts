import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";
import {TournamentManagementPanelComponent} from "./tournament-management-panel.component";

@NgModule({
  declarations: [
    TournamentManagementPanelComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ],
  exports: [
    TournamentManagementPanelComponent
  ]
})
export class TournamentManagementPanelModule {

}
