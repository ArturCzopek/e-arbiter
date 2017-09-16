import {NgModule} from '@angular/core';
import {TournamentPreviewCardComponent} from './component/tournament-preview-card.component';
import {CommonModule} from '@angular/common';
import {PaginationComponent} from './component/pagination.component';
import {MenuComponent} from './component/menu.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    MenuComponent,
    PaginationComponent,
    TournamentPreviewCardComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    MenuComponent,
    PaginationComponent,
    TournamentPreviewCardComponent
  ]
})
export class SharedModule {

}
