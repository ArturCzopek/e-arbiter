import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SharedModule} from '../../shared/shared.module';
import {ActiveTournamentsComponent} from './active-tournaments.component';

@NgModule({
  declarations: [
    ActiveTournamentsComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ],
  exports: [
    ActiveTournamentsComponent
  ]
})
export class ActiveTournamentsModule {

}
