import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";
import {TournamentManagementPanelComponent} from "./tournament-management-panel.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TournamentFormComponent} from "./tournament-form.component";
import {NgSemanticModule} from "ng-semantic/ng-semantic";

@NgModule({
  declarations: [
    TournamentFormComponent,
    TournamentManagementPanelComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    NgSemanticModule
  ],
  exports: [
    TournamentManagementPanelComponent
  ]
})
export class TournamentManagementPanelModule {

}
