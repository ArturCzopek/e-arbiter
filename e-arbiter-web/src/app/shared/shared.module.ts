import {NgModule} from "@angular/core";
import {TournamentPreviewCardComponent} from "./component/tournament-preview-card.component";
import {CommonModule} from "@angular/common";
import {PaginationComponent} from "./component/pagination.component";

@NgModule({
  declarations: [
    TournamentPreviewCardComponent,
    PaginationComponent,
  ],
  imports: [
    CommonModule
  ],
  exports: [
    TournamentPreviewCardComponent,
    PaginationComponent
  ]
})
export class SharedModule {

}
