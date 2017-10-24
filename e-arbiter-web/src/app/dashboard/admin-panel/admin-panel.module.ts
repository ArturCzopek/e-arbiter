import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SharedModule} from '../../shared/shared.module';
import {AdminPanelComponent} from './admin-panel.component';
import {HttpModule} from '@angular/http';
import {AdminPanelServicesDetailsCardComponent} from './admin-panel-service-details-card.component';

@NgModule({
  declarations: [
    AdminPanelComponent,
    AdminPanelServicesDetailsCardComponent
  ],
  imports: [
    CommonModule,
    HttpModule,
    SharedModule
  ],
  exports: [
    AdminPanelComponent
  ]
})
export class AdminPanelModule {

}
