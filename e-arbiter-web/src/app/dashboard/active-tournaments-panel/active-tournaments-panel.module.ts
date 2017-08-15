import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";
import {ActiveTournamentsPanelComponent} from "./active-tournaments-panel.component";

@NgModule({
  declarations: [
    ActiveTournamentsPanelComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ],
  exports: [
    ActiveTournamentsPanelComponent
  ]
})
export class ActiveTournamentsPanelModule {

}
