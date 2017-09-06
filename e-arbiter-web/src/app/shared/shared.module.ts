import {NgModule} from "@angular/core";
import {TournamentPreviewCardComponent} from "./component/tournament-preview-card.component";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [
    TournamentPreviewCardComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    TournamentPreviewCardComponent
  ]
})
export class SharedModule {

}
