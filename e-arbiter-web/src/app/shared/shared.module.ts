import {NgModule} from "@angular/core";
import {TournamentPreviewCardComponent} from "./component/tournament-preview-card.component";
import {CommonModule} from "@angular/common";
import {MomentModule} from "angular2-moment";

@NgModule({
  declarations: [
    TournamentPreviewCardComponent
  ],
  imports: [
    CommonModule,
    MomentModule
  ],
  exports: [
    TournamentPreviewCardComponent
  ]
})
export class SharedModule {

}
